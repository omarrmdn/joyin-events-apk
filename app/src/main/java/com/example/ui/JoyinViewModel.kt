package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.Event
import com.example.data.JoyinDatabase
import com.example.data.JoyinRepository
import com.example.data.Message
import com.example.data.SupabaseTag
import com.example.data.SupabaseClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JoyinViewModel(application: Application) : AndroidViewModel(application) {
    // Supabase DB Tags state
    private val _dbTags = MutableStateFlow<List<SupabaseTag>>(emptyList())
    val dbTags = _dbTags.asStateFlow()

    init {
        fetchSupabaseTags()
        fetchSupabaseEvents()
    }

    fun fetchSupabaseEvents() {
        viewModelScope.launch {
            try {
                // Fetch tags, attendees, event_tags, and users
                val apikey = SupabaseClient.API_KEY
                val auth = "Bearer $apikey"
                
                val tagsList = try {
                    SupabaseClient.api.getTags(apikey, auth)
                } catch (e: Exception) {
                    emptyList()
                }

                val eventTagsList = try {
                    SupabaseClient.api.getEventTags(apikey, auth)
                } catch (e: Exception) {
                    emptyList()
                }

                val usersList = try {
                    SupabaseClient.api.getUsers(apikey, auth)
                } catch (e: Exception) {
                    emptyList()
                }

                val attendeesList = try {
                    SupabaseClient.api.getAttendees(apikey, auth)
                } catch (e: Exception) {
                    emptyList()
                }

                // Map users, tags for easy lookup
                val userMap = usersList.associateBy { it.id }
                val tagMap = tagsList.associateBy { it.id }

                val supabaseEvents = SupabaseClient.api.getEvents(
                    apiKey = apikey,
                    authorization = auth
                )
                if (supabaseEvents.isNotEmpty()) {
                    val localEvents = supabaseEvents.map { se ->
                        val day = try {
                            se.date.split("-").last().filter { it.isDigit() }
                        } catch (e: Exception) {
                            "25"
                        }

                        val weekday = try {
                            val javaDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH).parse(se.date)
                            java.text.SimpleDateFormat("EEE", java.util.Locale.ENGLISH).format(javaDate ?: java.util.Date())
                        } catch (e: Exception) {
                            "Fri"
                        }

                        val existing = repository.getEventById(se.id.hashCode())
                        val isJoinedLocal = existing?.isJoined ?: false

                        // Real organizer name from users table!
                        val cleanHostName = if (!se.organizerId.isNullOrEmpty()) {
                            userMap[se.organizerId]?.name ?: "Resident Caretaker"
                        } else {
                            "Resident Caretaker"
                        }

                        // Real attendees from attendees bridge table!
                        val matchingAttendees = attendeesList.filter { it.eventId == se.id }
                        
                        // Map real user names
                        val attendeeNamesMapped = matchingAttendees.mapNotNull { att ->
                            userMap[att.userId]?.name ?: "Neighbor"
                        }
                        
                        // Dynamic attendee details
                        val attending = if (isJoinedLocal) {
                            val baseList = attendeeNamesMapped.toMutableList()
                            if (!baseList.contains("You") && !baseList.contains("Omar Ramadan")) {
                                baseList.add("You")
                            }
                            baseList.distinct().size
                        } else {
                            attendeeNamesMapped.distinct().size
                        }
                        
                        val finalAttendeeNames = if (isJoinedLocal) {
                            val list = attendeeNamesMapped.toMutableList()
                            if (!list.contains("You") && !list.contains("Omar Ramadan")) {
                                list.add("You")
                            }
                            list.distinct().joinToString(", ")
                        } else {
                            attendeeNamesMapped.distinct().joinToString(", ")
                        }

                        // Overriding "Unknown Location" with custom localized fallback neighborhoods
                        val cleanLocation = if (se.location.isNullOrBlank() || se.location.trim().lowercase() == "unknown location" || se.location.trim().lowercase() == "unknown") {
                            when {
                                se.title.contains("Calligraphy", true) || se.title.contains("Art", true) -> "Zamalek Art Space, Cairo"
                                se.title.contains("Running", true) || se.title.contains("Race", true) || se.title.contains("Sport", true) -> "Heliopolis Club, Cairo"
                                se.title.contains("Tech", true) || se.title.contains("Code", true) || se.title.contains("Dev", true) -> "The GrEEK Campus, Cairo"
                                se.title.contains("Lang", true) || se.title.contains("Exchange", true) || se.title.contains("Speak", true) -> "Maadi Coffee Hub, Cairo"
                                se.title.contains("Walk", true) || se.title.contains("Tour", true) -> "Zamalek Island, Cairo"
                                else -> "New Cairo Block, Cairo"
                            }
                        } else {
                            se.location.trim()
                        }

                        // Real Event Tags from event_tags bridge table!
                        val matchingTagIds = eventTagsList.filter { it.eventId == se.id }.map { it.tagId }
                        val matchingTags = matchingTagIds.mapNotNull { tagMap[it]?.name }
                        
                        val cleanTags = if (matchingTags.isNotEmpty()) {
                            matchingTags.distinct().joinToString(", ")
                        } else {
                            // Smart rule-based real-time tags extractor fallback
                            val tagsDerived = mutableListOf<String>()
                            val lowercaseTitle = se.title.lowercase()
                            val lowercaseDesc = (se.description ?: "").lowercase()

                            if (lowercaseTitle.contains("run") || lowercaseTitle.contains("race") || lowercaseTitle.contains("sport") || lowercaseTitle.contains("fit") || lowercaseTitle.contains("gym") || lowercaseTitle.contains("swim")) {
                                tagsDerived.add("Sports")
                                if (lowercaseTitle.contains("run")) tagsDerived.add("Running")
                                if (lowercaseTitle.contains("race")) tagsDerived.add("Race")
                            }
                            if (lowercaseTitle.contains("art") || lowercaseTitle.contains("paint") || lowercaseTitle.contains("draw") || lowercaseTitle.contains("craft") || lowercaseTitle.contains("calligraphy") || lowercaseTitle.contains("design") || lowercaseTitle.contains("creat")) {
                                tagsDerived.add("Art")
                                if (lowercaseTitle.contains("calligraphy")) tagsDerived.add("Calligraphy")
                                if (lowercaseTitle.contains("workshop") || lowercaseDesc.contains("workshop")) tagsDerived.add("Workshop")
                            }
                            if (lowercaseTitle.contains("tech") || lowercaseTitle.contains("code") || lowercaseTitle.contains("program") || lowercaseTitle.contains("dev") || lowercaseTitle.contains("soft") || lowercaseTitle.contains("comput") || lowercaseTitle.contains("ai") || lowercaseTitle.contains("blockchain")) {
                                tagsDerived.add("Tech")
                                if (lowercaseTitle.contains("ai") || lowercaseDesc.contains("ai")) tagsDerived.add("AI")
                                if (lowercaseTitle.contains("workshop") || lowercaseDesc.contains("workshop")) tagsDerived.add("Workshop")
                            }
                            if (lowercaseTitle.contains("lang") || lowercaseTitle.contains("exchange") || lowercaseTitle.contains("speak") || lowercaseTitle.contains("talk") || lowercaseTitle.contains("convers") || lowercaseTitle.contains("english") || lowercaseTitle.contains("arabic")) {
                                tagsDerived.add("Lang - Exchange")
                                if (lowercaseTitle.contains("arabic")) tagsDerived.add("Arabic")
                                if (lowercaseTitle.contains("english")) tagsDerived.add("English")
                            }
                            if (lowercaseTitle.contains("social") || lowercaseTitle.contains("meet") || lowercaseTitle.contains("gather") || lowercaseTitle.contains("party") || lowercaseTitle.contains("coffee") || lowercaseTitle.contains("food") || lowercaseTitle.contains("eat")) {
                                tagsDerived.add("Social")
                                if (lowercaseTitle.contains("coffee")) tagsDerived.add("Coffee")
                                if (lowercaseTitle.contains("food") || lowercaseTitle.contains("eat")) tagsDerived.add("Food")
                            }

                            if (tagsDerived.isEmpty()) {
                                tagsDerived.add("Social")
                            }
                            tagsDerived.distinct().joinToString(", ")
                        }

                        Event(
                            id = se.id.hashCode(),
                            title = se.title,
                            hostName = cleanHostName,
                            location = cleanLocation,
                            dateTime = "${se.date} at ${se.time}",
                            price = if (se.price < 0.0) 0.0 else se.price,
                            description = se.description ?: "No description provided.",
                            tags = cleanTags,
                            audience = "Everyone",
                            attendingCount = attending,
                            dateDay = day,
                            dateWeekday = weekday,
                            isJoined = isJoinedLocal,
                            imageUrl = se.imageUrl ?: "cream",
                            attendeeNames = finalAttendeeNames,
                            currencyCode = se.currencyCode ?: "EGP",
                            maxCapacity = se.maxCapacity ?: 10
                        )
                    }
                    database.eventDao().insertEvents(localEvents)
                }
            } catch (e: Exception) {
                android.util.Log.e("JoyinViewModel", "Failed to fetch Supabase events, running on local cache", e)
            }
        }
    }

    fun fetchSupabaseTags() {
        viewModelScope.launch {
            try {
                val tagsList = SupabaseClient.api.getTags(
                    apiKey = SupabaseClient.API_KEY,
                    authorization = "Bearer ${SupabaseClient.API_KEY}"
                )
                _dbTags.value = tagsList
            } catch (e: Exception) {
                e.printStackTrace()
                _dbTags.value = listOf(
                    SupabaseTag(1, "Cairo", "City"),
                    SupabaseTag(2, "Sports", "Category"),
                    SupabaseTag(3, "Art", "Category"),
                    SupabaseTag(4, "Tech", "Category"),
                    SupabaseTag(5, "Race", "Activity"),
                    SupabaseTag(6, "Running", "Activity"),
                    SupabaseTag(7, "Calligraphy", "Activity"),
                    SupabaseTag(8, "Workshop", "Activity"),
                    SupabaseTag(9, "Social", "Category"),
                    SupabaseTag(10, "Free", "Pricing")
                )
            }
        }
    }

    private val database = Room.databaseBuilder(
        application,
        JoyinDatabase::class.java,
        "joyin_database"
    ).fallbackToDestructiveMigration().build()

    private val repository = JoyinRepository(database)

    // Raw streams
    val allEventsRaw: StateFlow<List<Event>> = repository.allEvents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val joinedEvents: StateFlow<List<Event>> = repository.joinedEvents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filter states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _activeTab = MutableStateFlow("Home") // Home, My events, Create, Messages, Profile
    val activeTab = _activeTab.asStateFlow()

    // Persistent Settings
    private val prefs = application.getSharedPreferences("joyin_settings", android.content.Context.MODE_PRIVATE)

    // Authentication States
    private val _isLoggedIn = MutableStateFlow(prefs.getBoolean("is_logged_in", false))
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _userDisplayName = MutableStateFlow(prefs.getString("user_display_name", "") ?: "")
    val userDisplayName = _userDisplayName.asStateFlow()

    private val _userEmail = MutableStateFlow(prefs.getString("user_email", "") ?: "")
    val userEmail = _userEmail.asStateFlow()

    private val _userPhotoUrl = MutableStateFlow(prefs.getString("user_photo_url", null))
    val userPhotoUrl = _userPhotoUrl.asStateFlow()

    fun updateLoginState(loggedIn: Boolean, displayName: String = "", email: String = "", photoUrl: String? = null) {
        _isLoggedIn.value = loggedIn
        _userDisplayName.value = displayName
        _userEmail.value = email
        _userPhotoUrl.value = photoUrl
        
        prefs.edit()
            .putBoolean("is_logged_in", loggedIn)
            .putString("user_display_name", displayName)
            .putString("user_email", email)
            .putString("user_photo_url", photoUrl)
            .apply()
    }

    fun logout() {
        updateLoginState(false)
    }

    private val _selectedLanguage = MutableStateFlow(prefs.getString("lang", "English") ?: "English")
    val selectedLanguage = _selectedLanguage.asStateFlow()

    private val _selectedTheme = MutableStateFlow(prefs.getString("theme", "System") ?: "System")
    val selectedTheme = _selectedTheme.asStateFlow()

    fun selectLanguage(lang: String) {
        _selectedLanguage.value = lang
        prefs.edit().putString("lang", lang).apply()
    }

    fun selectTheme(theme: String) {
        _selectedTheme.value = theme
        prefs.edit().putString("theme", theme).apply()
    }

    private val _isNearbyOnly = MutableStateFlow(false)
    val isNearbyOnly = _isNearbyOnly.asStateFlow()

    fun toggleNearbyOnly() {
        _isNearbyOnly.value = !_isNearbyOnly.value
    }

    // Filtered Events — excludes expired events
    val filteredEvents: StateFlow<List<Event>> = combine(
        allEventsRaw,
        _searchQuery,
        _selectedCategory,
        _isNearbyOnly
    ) { events, query, category, nearbyOnly ->
        val todayMillis = System.currentTimeMillis()
        events.filter { event ->
            // Parse event date to check if it's still upcoming
            val isNotExpired = try {
                // Try Supabase format: "yyyy-MM-dd at HH:mm:ss" or just "yyyy-MM-dd"
                val datePart = event.dateTime.substringBefore(" at ").substringBefore(" - ").trim()
                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH)
                val parsed = sdf.parse(datePart)
                if (parsed != null) {
                    // Add 1 day buffer so same-day events still show
                    parsed.time + 86_400_000L >= todayMillis
                } else {
                    true // can't parse, keep it
                }
            } catch (e: Exception) {
                true // parsing failed, keep event visible
            }

            val matchesQuery = query.isEmpty() ||
                event.title.contains(query, ignoreCase = true) ||
                event.location.contains(query, ignoreCase = true) ||
                event.tags.contains(query, ignoreCase = true)

            val matchesCategory = category == "All" ||
                event.tags.contains(category, ignoreCase = true) ||
                event.description.contains(category, ignoreCase = true)

            val matchesNearby = !nearbyOnly ||
                event.location.contains("Maadi", ignoreCase = true) ||
                event.location.contains("Zamalek", ignoreCase = true) ||
                event.location.contains("Heliopolis", ignoreCase = true) ||
                event.location.contains("Salam", ignoreCase = true) ||
                event.location.contains("Cairo", ignoreCase = true)

            isNotExpired && matchesQuery && matchesCategory && matchesNearby
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected event detail context
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    // Active Chat Session message stream
    private val _chatMessages = MutableStateFlow<List<Message>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()

    private var activeChatJob: kotlinx.coroutines.Job? = null

    init {
        viewModelScope.launch {
            repository.initDatabaseIfEmpty()
        }
    }

    fun selectTab(tab: String) {
        _activeTab.value = tab
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
        // Automatically fetch messages in case we open chat for it
        selectChatForEvent(event.id)
    }

    fun closeEventDetails() {
        _selectedEvent.value = null
    }

    private fun selectChatForEvent(eventId: Int) {
        activeChatJob?.cancel()
        activeChatJob = viewModelScope.launch {
            repository.getMessagesForEvent(eventId).collect {
                _chatMessages.value = it
            }
        }
    }

    fun toggleJoinEvent(event: Event) {
        viewModelScope.launch {
            val newlyJoined = !event.isJoined
            val currentNames = event.attendeeNames.split(",").map { it.trim() }.filter { it.isNotBlank() }.toMutableList()
            val myName = if (userDisplayName.value.isNotBlank()) userDisplayName.value else "Omar Ramadan"
            
            if (newlyJoined) {
                if (!currentNames.contains(myName)) {
                    currentNames.add(myName)
                }
            } else {
                currentNames.remove("You")
                currentNames.remove("Omar Ramadan")
                currentNames.remove(myName)
            }
            val finalNames = currentNames.distinct().joinToString(", ")

            val updated = event.copy(
                isJoined = newlyJoined,
                attendingCount = if (newlyJoined) event.attendingCount + 1 else maxOf(0, event.attendingCount - 1),
                attendeeNames = finalNames
            )
            repository.updateEvent(updated)
            if (_selectedEvent.value?.id == event.id) {
                _selectedEvent.value = updated
            }
        }
    }

    fun addEvent(
        title: String,
        hostName: String,
        location: String,
        dateTime: String,
        price: Double,
        description: String,
        tags: String,
        audience: String,
        dateDay: String = "6",
        dateWeekday: String = "Fri",
        imageUrl: String? = "cream",
        maxCapacity: Int = 100
    ) {
        viewModelScope.launch {
            val newEvent = Event(
                title = title,
                hostName = hostName,
                location = location,
                dateTime = dateTime,
                price = price,
                description = description,
                tags = tags,
                audience = audience,
                dateDay = dateDay,
                dateWeekday = dateWeekday,
                attendingCount = 1,
                isJoined = false, // Just created! Users can join it themselves or let others join
                imageUrl = imageUrl,
                attendeeNames = hostName,
                maxCapacity = maxCapacity
            )
            repository.createEvent(newEvent)
            _activeTab.value = "Home" // Redirect back to Home tab after creation!

            // Sync with Supabase on the backend in the background!
            try {
                val numericDay = dateDay.filter { it.isDigit() }
                val dayStr = if (numericDay.isNotEmpty()) numericDay.padStart(2, '0') else "25"
                // Construct compliant YYYY-MM-DD date format
                val formattedDate = "2026-05-$dayStr"

                val supabaseEvent = com.example.data.SupabaseEvent(
                    title = title,
                    description = description,
                    location = location,
                    price = price,
                    date = formattedDate,
                    time = "18:00:00",
                    imageUrl = imageUrl,
                    organizerId = null,
                    maxCapacity = maxCapacity
                )
                SupabaseClient.api.createEvent(
                    apiKey = SupabaseClient.API_KEY,
                    authorization = "Bearer ${SupabaseClient.API_KEY}",
                    event = supabaseEvent
                )
            } catch (e: Exception) {
                android.util.Log.e("JoyinViewModel", "Supabase push failed, keeping local-only record", e)
            }
        }
    }

    fun sendMessageToEvent(eventId: Int, content: String, isFromMe: Boolean = true) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val newMessage = Message(
                eventId = eventId,
                senderName = if (isFromMe) "You" else "Host",
                content = content,
                isFromMe = isFromMe
            )
            repository.sendMessage(newMessage)
            
            // Automatically insert a response mock simulation after a brief period to keep it super interactive and P2P-focused!
            if (isFromMe) {
                simulateReply(eventId)
            }
        }
    }

    private fun simulateReply(eventId: Int) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(1500) // Brief typing pause
            val event = repository.getEventById(eventId) ?: return@launch
            val host = event.hostName
            val replies = listOf(
                "Awesome, look forward to seeing you there!",
                "Great! Let me know if you need any directions or info.",
                "Perfect! We are gathering near the main entrance.",
                "Got it! Thanks for reaching out.",
                "Let me review and get back to you soon."
            )
            val randomReply = replies.random()
            val replyMessage = Message(
                eventId = eventId,
                senderName = host,
                content = randomReply,
                isFromMe = false
            )
            repository.sendMessage(replyMessage)
        }
    }
}
