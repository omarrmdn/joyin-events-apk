package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import com.example.ui.icons.FeatherIcons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.Event
import com.example.data.Message
import com.example.data.formattedPrice
import com.example.ui.JoyinViewModel
import com.example.ui.theme.*
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.delay

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.foundation.isSystemInDarkTheme
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.core.graphics.PathParser
import android.location.LocationManager
import android.location.Location
import android.content.Context
import android.location.Geocoder
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest

var isArabicModeActiveByOmar by mutableStateOf(false)

fun t(en: String): String {
    if (!isArabicModeActiveByOmar) return en
    return when (en) {
        "Profile" -> "الملف الشخصي"
        "Find local vibes..." -> "دور على الخروجة الجاية..."
        "Categories" -> "الفئات"
        "My Events" -> "فعالياتي"
        "Inbox Messages" -> "الرسائل الواردة"
        "Inbox" -> "الرسائل الواردة"
        "List Neighborhood Event" -> "نشر فعالية جديدة بالحي"
        "Neighborhood Preferences" -> "تفضيلات الحي"
        "Current Neighborhood" -> "الحي الحالي"
        "Alert Notifications" -> "إشعارات التنبيه"
        "Verification Status" -> "حالة التحقق"
        "Events Created" -> "فعاليات نشرتها"
        "Events Attended" -> "فعاليات حضرتها"
        "Join Event" -> "انضمام للفعالية"
        "Leave Event" -> "إلغاء الانضمام"
        "About Event" -> "عن الفعالية"
        "Tags" -> "الوسوم"
        "Event Details" -> "تفاصيل الفعالية"
        "Attending" -> "حضور"
        "Your Name (Host) *" -> "اسمك (المنظم) *"
        "Event Title *" -> "عنوان الفعالية *"
        "Location Pin *" -> "موقع الفعالية *"
        "Schedule Date Summary *" -> "تاريخ ووقت الفعالية *"
        "Day of Month" -> "يوم الشهر"
        "Weekday Label" -> "اليوم"
        "Cost / Entry ($)" -> "سعر الدخول ($)"
        "Audience Segment" -> "الفئة المستهدفة"
        "Description *" -> "تفاصيل ووصف الفعالية *"
        "Publish Event" -> "نشر الفعالية"
        "Settings" -> "الإعدادات"
        "Language" -> "اللغة (Language)"
        "Theme" -> "المظهر (Theme)"
        "Light Mode" -> "الوضع المضيء"
        "Dark Mode" -> "الوضع الداكن"
        "System" -> "تلقائي حسب النظام"
        "English" -> "الإنجليزية (English)"
        "Egyptian Arabic" -> "العربية (مصرى)"
        "Everyone" -> "للجميع"
        "Males Only" -> "للشباب فقط"
        "All" -> "الكل"
        "Sports" -> "رياضة"
        "Art" -> "فن"
        "Boardgames" -> "ألعاب"
        "Music" -> "موسيقى"
        "Tech" -> "تقنية"
        "Host" -> "المنظم"
        "Chat with Host" -> "محادثة المنظم"
        "Heliopolis, Cairo" -> "مصر الجديدة، القاهرة"
        "Enabled for weekly running sports" -> "مفعل لرياضات الركض الأسبوعية"
        "Fully Verified Resident" -> "سكان الحي المؤكدين"
        "Omar Ramadan" -> "عمر رمضان"
        "Send a secure peer message..." -> "اكتب رسالة للمنظم هنا..."
        "Home" -> "الرئيسية"
        "My events" -> "فعالياتي"
        "Create" -> "إضافة"
        "Messages" -> "الرسائل"
        "Message" -> "الرسائل"
        "You" -> "حسابي"
        "Weekly Football" -> "كورة كوليكتيف"
        "Chess Tournament" -> "بطولة الشطرنج"
        "Arabic Calligraphy" -> "خط عربي"
        "Photography Walk" -> "جولة تصوير بالحي"
        "Cairo Running Club" -> "رنينج كلوب"
        "Cooking Masterclass" -> "ورشة طبخ"
        "Book Club Meetup" -> "نادي الكتاب"
        "Boardgame Night" -> "ألعاب جماعية"
        
        // Extended maps and navigation translations
        "OpenStreetMap View" -> "خريطة OpenStreetMap"
        "OSM Active" -> "نظام OSM نشط"
        "Navigate on OpenStreetMap" -> "توجيه بنظام OpenStreetMap"
        "Start OSM Navigation" -> "بدء ملاحة OpenStreetMap"
        "Join" -> "انضمام للفعالية"
        "Free" -> "مجاني"
        "About Event" -> "عن الفعالية"
        "List Neighborhood Event" -> "نشر فعالية بالحي"
        "Event Title *" -> "عنوان الفعالية *"
        "Your Name (Host) *" -> "اسمك (المنظم) *"
        "Location Pin *" -> "موقع الفعالية *"
        "Schedule Date Summary *" -> "ملخص تاريخ الموعد *"
        "Day of Month" -> "يوم الشهر"
        "Weekday Label" -> "اسم اليوم"
        "Cost / Entry ($)" -> "سعر التذكرة ($)"
        "Audience Segment" -> "الفئة المستهدفة"
        "Description *" -> "تفاصيل الفعالية *"
        "Tags" -> "الوسوم"
        "Search" -> "البحث"
        "Options" -> "خيارات"
        "Empty calendar" -> "التقويم فارغ"
        "Spread word of local gatherings or workshops in your block." -> "انشر الخبر عن التجمعات أو ورش العمل المحلية في منطقتك."
        "e.g. Arabic Calligraphy Class" -> "مثال: ورشة خط عربي"
        "e.g. Abdelrahman" -> "مثال: عبد الرحمن"
        "e.g. Heliopolis, Cairo" -> "مثال: مصر الجديدة، القاهرة"
        "e.g. Saturdays - 3:00 Pm" -> "مثال: السبت - ٣ مساءً"
        "0.00 for Free" -> "اكتب 0.00 للمجاني"
        "Cairo, Sports, Race, Running" -> "القاهرة، رياضة، جري"
        "Provide details of times, activities, rules..." -> "اكتب تفاصيل الفعالية، الأنشطة والتعليمات..."
        "Create Event" -> "نشر الفعالية"
        "Tags (Comma Separated) *" -> "الوسوم (مفصولة بفاصلة) *"
        "Send a message" -> "اكتب رسالة هنا..."
        "Type a message..." -> "اكتب رسالة هنا..."
        "Send" -> "إرسال"
        "Notifications" -> "الإشعارات"
        "Running Race" -> "سباق جري"
        "Calligraphy Workshop" -> "ورشة خط عربي"
        "Tech Networking Meetup" -> "ملتقى شبكات التقنية"
        "Tue" -> "الثلاثاء"
        "Wed" -> "الأربعاء"
        "Thu" -> "الخميس"
        "Fri" -> "الجمعة"
        "Sat" -> "السبت"
        "Sun" -> "الأحد"
        "Mon" -> "الإثنين"
        "3" -> "٣"
        "4" -> "٤"
        "5" -> "٥"
        "6" -> "٦"
        "7" -> "٧"
        "8" -> "٨"
        "No events found." -> "لم يتم العثور على فعاليات."
        "No events scheduled for Day" -> "لا توجد فعاليات مجدولة لليوم"
        "Toggle other dates or join an event in search." -> "تصفح التواريخ الأخرى أو انضم إلى فعالية من البحث."
        "Lang - Exchange" -> "تبادل لغات"
        "Social" -> "اجتماعي"
        
        // Navigation simulator hud labels
        "Navigation Simulator" -> "محاكي الملاحة بنظام OSM"
        "Current Speed" -> "السرعة الحالية"
        "Distance Details" -> "تفاصيل المسافة"
        "End Navigation" -> "إنهاء الملاحة"
        "Step" -> "الخطوة"
        "Next Step" -> "الخطوة التالية"
        "Meters Remaining" -> "متر متبقي"
        "Arrived" -> "لقد وصلت!"
        "Head North-East on Al-Nasr road towards the event." -> "اتجه شمالاً شرقاً على طريق النصر باتجاه موقع الفعالية."
        "Turn right using the residential block pathway." -> "انعطف يميناً باستخدام ممر المبنى السكني."
        "You have arrived safely using OSM instructions!" -> "لقد وصلت بأمان باستخدام إرشادات خريطة OSM!"
        "OSM Navigation Interface" -> "واجهة ملاحة OSM"
        "Estimated Distance" -> "المسافة التقريبية"
        "Est. Time" -> "الوقت المتوقع"
        "OSM Map Frame" -> "إطار خريطة OSM"
        "Navigate" -> "ملاحة"
        "Close OSM Simulator" -> "إغلاق محاكي OSM"
        "ETA" -> "الوصول"
        "Speed" -> "السرعة"
        "Distance" -> "المسافة"
        "Google Sign In" -> "تسجيل الدخول بجوجل"
        "Sign in with Google" -> "تسجيل الدخول بواسطة جوجل"
        "Sign in to Joyin" -> "تسجيل الدخول في جوين"
        "Please sign in to unlock custom preferences, send secure messages, or create neighborhood events." -> "يرجى تسجيل الدخول لتخطيط تفضيلاتك، وإرسال الرسائل الآمنة، ونشر فعاليات الحي."
        "Sign Out" -> "تسجيل الخروج"
        "Logged in as" -> "مسجل الدخول باسم"
        "Google Account Verified" -> "تم التحقق من حساب جوجل"
        "Simulation Sign In" -> "تسجيل دخول تجريبي"
        "You have simulated Google Sign-In successfully!" -> "لقد تم تسجيل الدخول التجريبي بجوجل بنجاح!"
        "Omar Ramadan" -> "عمر رمضان"
        "Everyone" -> "الجميع"
        "Males Only" -> "الذكور فقط"
        "Verification Status" -> "حالة التحقق"
        "Fully Verified Resident" -> "مقيم تم التحقق منه بالكامل"
        "Your Google Account is fully authenticated with Joyin's secure P2P server." -> "حساب جوجل الخاص بك مصدق بالكامل مع خادم P2P الآمن لشركة جوين."
        "welcome to joyin" -> "أهلاً بك في جوين"
        "Welcome to Joyin" -> "أهلاً بك في جوين"
        "Continue with Google" -> "المتابعة باستخدام جوجل"
        else -> en
    }
}

@Composable
fun JoyinSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JoyinAccent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.R.drawable.logoredsharp),
            contentDescription = "Joyin Logo",
            modifier = Modifier
                .size(160.dp)
                .testTag("splash_logo_image")
        )
    }
}

@Composable
fun JoyinLoginSignupScreen(
    viewModel: JoyinViewModel,
    onGuestContinue: () -> Unit
) {
    val context = LocalContext.current
    
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("730568974104-i8tsp26nrdqqhb0klillr8j37cf5vs4a.apps.googleusercontent.com")
            .build()
    }
    
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val displayName = account.displayName ?: "Omar Ramadan"
                val emailStr = account.email ?: "omarrmdn2024@gmail.com"
                val photoUrl = account.photoUrl?.toString()
                
                viewModel.updateLoginState(
                    loggedIn = true,
                    displayName = displayName,
                    email = emailStr,
                    photoUrl = photoUrl
                )
                Toast.makeText(context, "Welcome to Joyin: $displayName", Toast.LENGTH_LONG).show()
            } else {
                viewModel.updateLoginState(
                    loggedIn = true,
                    displayName = "Omar Ramadan",
                    email = "omarrmdn2024@gmail.com",
                    photoUrl = null
                )
                Toast.makeText(context, "Welcome to Joyin: Omar Ramadan", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            viewModel.updateLoginState(
                loggedIn = true,
                displayName = "Omar Ramadan",
                email = "omarrmdn2024@gmail.com",
                photoUrl = null
            )
            Toast.makeText(context, "Welcome to Joyin: Omar Ramadan", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JoyinAccent)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("onboarding_container"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = com.example.R.drawable.logoredsharp),
                contentDescription = "Joyin Red Logo",
                modifier = Modifier
                    .size(150.dp)
                    .testTag("onboarding_logo")
            )
            
            Spacer(modifier = Modifier.height(28.dp))
            
            Text(
                text = t("welcome to joyin"),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("onboarding_title")
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = {
                    try {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    } catch (e: Exception) {
                        viewModel.updateLoginState(
                            loggedIn = true,
                            displayName = "Omar Ramadan",
                            email = "omarrmdn2024@gmail.com",
                            photoUrl = null
                        )
                        Toast.makeText(context, "Welcome to Joyin: Omar Ramadan", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("google_login_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "G",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = t("Continue with Google"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun JoyinAppMain(viewModel: JoyinViewModel) {
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val selectedEvent by viewModel.selectedEvent.collectAsStateWithLifecycle()
    val allEvents by viewModel.filteredEvents.collectAsStateWithLifecycle()
    val joinedEvents by viewModel.joinedEvents.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()

    val selectedThemeState by viewModel.selectedTheme.collectAsStateWithLifecycle()
    val selectedLanguageState by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    val systemInDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(selectedThemeState, systemInDarkTheme) {
        isDarkThemeActiveByOmar = true // Forced dark theme - light mode is removed!
    }

    LaunchedEffect(selectedLanguageState) {
        isArabicModeActiveByOmar = (selectedLanguageState == "Egyptian Arabic")
    }

    var activeChatEventId by remember { mutableStateOf<Int?>(null) }
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    var showSplash by remember { mutableStateOf(true) }
    var isGuestMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2500)
        showSplash = false
    }

    val layoutDirection = if (isArabicModeActiveByOmar) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        if (showSplash) {
            JoyinSplashScreen()
        } else if (!isLoggedIn && !isGuestMode) {
            JoyinLoginSignupScreen(
                viewModel = viewModel,
                onGuestContinue = { isGuestMode = true }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JoyinWhite)
            ) {
                Scaffold(
                    bottomBar = {
                        if (selectedEvent == null && activeChatEventId == null) {
                            JoyinBottomNavBar(
                                activeTab = activeTab,
                                onTabSelected = { tab ->
                                    viewModel.selectTab(tab)
                                }
                            )
                        }
                    },
                    containerColor = JoyinWhite,
                    contentWindowInsets = WindowInsets.navigationBars
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Main Tab switching
                        when (activeTab) {
                        "Home" -> HomeScreen(
                            events = allEvents,
                            viewModel = viewModel,
                            onEventSelected = { event ->
                                viewModel.selectEvent(event)
                            }
                        )
                        "My events" -> MyEventsScreen(
                            joinedEvents = joinedEvents,
                            onEventSelected = { event ->
                                viewModel.selectEvent(event)
                            },
                            viewModel = viewModel
                        )
                        "Create" -> CreateScreen(viewModel = viewModel)
                        "Messages" -> {
                            val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
                            if (!isLoggedIn) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
                                        shape = RoundedCornerShape(20.dp),
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                                        border = BorderStroke(1.dp, JoyinBorderGray)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(24.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = FeatherIcons.Mail,
                                                contentDescription = null,
                                                tint = JoyinAccent,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text(
                                                text = t("Inbox Messages"),
                                                color = JoyinCharcoal,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = t("Please sign in to unlock custom preferences, send secure messages, or create neighborhood events."),
                                                color = JoyinLightGray,
                                                fontSize = 13.sp,
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(24.dp))
                                            Button(
                                                onClick = { viewModel.selectTab("Profile") },
                                                colors = ButtonDefaults.buttonColors(containerColor = JoyinAccent),
                                                shape = RoundedCornerShape(12.dp),
                                                modifier = Modifier.fillMaxWidth().height(48.dp)
                                            ) {
                                                Text(text = t("Sign in to Joyin"), fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (activeChatEventId == null) {
                                    InboxListScreen(
                                        events = allEvents,
                                        onChatSelected = { eventId ->
                                            viewModel.selectEvent(allEvents.find { it.id == eventId } ?: allEvents.first())
                                            activeChatEventId = eventId
                                        }
                                    )
                                }
                            }
                        }
                        "Profile" -> ProfileScreen(viewModel = viewModel)
                    }

                    // Detail screen overlay logic
                    selectedEvent?.let { event ->
                        // Only display general details if they are not in the chat conversation screen
                        if (activeChatEventId == null) {
                            EventDetailScreen(
                                event = event,
                                onBack = { viewModel.closeEventDetails() },
                                onJoinToggle = { viewModel.toggleJoinEvent(event) },
                                onChatWithHost = {
                                    activeChatEventId = event.id
                                },
                                viewModel = viewModel
                            )
                        }
                    }

                    // Detailed chat screen overlay logic
                    activeChatEventId?.let { eventId ->
                        val chatEvent = allEvents.find { it.id == eventId }
                        if (chatEvent != null) {
                            ChatDetailScreen(
                                event = chatEvent,
                                messages = chatMessages,
                                onSendMessage = { content ->
                                    viewModel.sendMessageToEvent(eventId, content, isFromMe = true)
                                },
                                onBack = {
                                    activeChatEventId = null
                                    // If details overlay was closed, make sure detail is also cleared
                                    if (activeTab == "Messages") {
                                        viewModel.closeEventDetails()
                                    }
                                }
                            )
                        } else {
                            activeChatEventId = null
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
fun JoyinBottomNavBar(
    activeTab: String,
    onTabSelected: (String) -> Unit
) {
    // Custom bottom layout keeping targets at robust height and clean black background
    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("joyin_bottom_navbar"),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                NavigationTabItem("Home", FeatherIcons.Home, t("Home")),
                NavigationTabItem("My events", FeatherIcons.Calendar, t("My Events")),
                NavigationTabItem("Create", FeatherIcons.Plus, t("Create")),
                NavigationTabItem("Messages", FeatherIcons.MessageSquare, t("Messages")),
                NavigationTabItem("Profile", FeatherIcons.User, t("You"))
            )

            tabs.forEach { item ->
                val isSelected = activeTab == item.id
                
                if (item.id == "Create") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .clickable { onTabSelected(item.id) }
                            .padding(vertical = 4.dp)
                            .testTag("nav_tab_create")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(JoyinAccent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Plus,
                                contentDescription = t("Create"),
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .clickable { onTabSelected(item.id) }
                            .padding(vertical = 4.dp)
                            .testTag("nav_tab_${item.id.lowercase().replace(" ", "_")}")
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) Color.White else JoyinLightGray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            color = if (isSelected) Color.White else JoyinLightGray,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JoyinLogo(
    modifier: Modifier = Modifier,
    accentColor: Color = JoyinAccent,
    textColor: Color = JoyinCharcoal
) {
    val path1Data = "M 0 26.666 a 5.333 5.333 0 1 1 10.667 0 a 5.333 5.333 0 1 1 -10.667 0 Z"
    val path2Data = "M24.19 5.333a5.333 5.333 0 1 0-10.667 0V32c5.892 0 10.667-4.776 10.667-10.667z"
    val path3Data = "M45.309.698h5.105v17.035q0 2.361-1.061 4.103-1.05 1.742-2.923 2.684t-4.354.943q-2.207 0-4.008-.776a6.4 6.4 0 0 1-2.84-2.386q-1.05-1.61-1.037-4.043h5.141q.024.965.394 1.658.381.68 1.038 1.05.668.357 1.574.357.954 0 1.61-.405.669-.418 1.015-1.217t.346-1.968zm17.559 24.789q-2.78 0-4.807-1.181-2.016-1.194-3.114-3.317-1.097-2.135-1.097-4.95 0-2.84 1.097-4.963 1.098-2.135 3.114-3.316 2.028-1.193 4.807-1.193t4.796 1.193q2.028 1.181 3.125 3.316 1.098 2.124 1.098 4.963 0 2.816-1.098 4.95a8.06 8.06 0 0 1-3.125 3.317q-2.016 1.18-4.796 1.18m.024-3.937q1.264 0 2.112-.716.846-.727 1.276-1.98.441-1.252.442-2.851 0-1.599-.442-2.851-.43-1.252-1.276-1.98-.847-.729-2.112-.728-1.276 0-2.147.728-.86.727-1.3 1.98-.43 1.252-.43 2.85 0 1.6.43 2.852.44 1.252 1.3 1.98.87.716 2.147.716M77.738 32q-.966 0-1.813-.155-.835-.144-1.384-.37l1.145-3.793q.895.274 1.61.298.729.023 1.253-.334.537-.358.871-1.217l.298-.775-6.573-18.848h5.345l3.793 13.456h.19l3.83-13.456h5.38l-7.121 20.303q-.514 1.479-1.396 2.577a5.7 5.7 0 0 1-2.207 1.706q-1.335.608-3.22.608m16.545-6.871V6.805h5.082V25.13zm14.234-10.593v10.593h-5.081V6.805h4.843v3.233h.214a5.1 5.1 0 0 1 2.04-2.529q1.432-.942 3.472-.942 1.909 0 3.328.835 1.42.835 2.207 2.386.787 1.538.787 3.674v11.667h-5.082v-10.76q.012-1.682-.858-2.625-.871-.954-2.398-.954-1.026 0-1.814.441a3.1 3.1 0 0 0-1.216 1.289q-.43.834-.442 2.016"
    val path4Data = "M94.893 4.39q.81.751 1.944.751t1.933-.751q.81-.763.81-1.825 0-1.05-.81-1.802Q97.97 0 96.837 0t-1.944.763q-.8.752-.8 1.802 0 1.062.8 1.825"

    val path1 = remember { PathParser.createPathFromPathData(path1Data).asComposePath() }
    val path2 = remember { PathParser.createPathFromPathData(path2Data).asComposePath() }
    val path3 = remember { PathParser.createPathFromPathData(path3Data).asComposePath() }
    val path4 = remember { PathParser.createPathFromPathData(path4Data).asComposePath() }

    Canvas(
        modifier = modifier
            .testTag("joino_canvas_logo")
            .width(98.dp)
            .height(26.dp)
    ) {
        val scaleX = size.width / 121f
        val scaleY = size.height / 32f

        scale(scaleX, scaleY, pivot = Offset.Zero) {
            drawPath(path = path1, color = accentColor)
            drawPath(path = path2, color = accentColor)
            drawPath(path = path3, color = textColor)
            drawPath(path = path4, color = accentColor)
        }
    }
}

data class NavigationTabItem(
    val id: String,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    events: List<Event>,
    viewModel: JoyinViewModel,
    onEventSelected: (Event) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val isNearbyOnlyState by viewModel.isNearbyOnly.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    val dbTagsState by viewModel.dbTags.collectAsStateWithLifecycle()
    val categories = remember(dbTagsState, events) {
        // Collect all distinct tag names from current events list
        val activeTagNames = events.flatMap { event ->
            event.tags.split(",").map { it.trim().lowercase() }
        }.filter { it.isNotBlank() }.toSet()

        val list = mutableListOf("All")
        if (dbTagsState.isNotEmpty()) {
            val filteredTags = dbTagsState.filter { tagObj ->
                activeTagNames.contains(tagObj.name.lowercase())
            }.map { tagObj -> tagObj.name }.distinct()
            list.addAll(filteredTags)
        } else {
            val defaultTags = listOf("Sports", "Lang - Exchange", "Tech", "Art", "Social")
            val filteredDefault = defaultTags.filter { tag ->
                activeTagNames.contains(tag.lowercase())
            }
            list.addAll(filteredDefault)
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // Top Logo + Notifications bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // "Joino" Custom Brand Logo
            JoyinLogo()

            // Notification Bell outline icon in white inside a 40dp outline circular container
            IconButton(
                onClick = {},
                modifier = Modifier.testTag("notification_button")
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.5.dp, JoyinBorderGray, CircleShape)
                        .background(Color.Transparent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Bell,
                        contentDescription = t("Notifications"),
                        tint = JoyinCharcoal,
                        modifier = Modifier.size(20.dp)
                    )
                    // Small red notification badge dot
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(JoyinAccent, CircleShape)
                            .align(Alignment.TopEnd)
                            .offset(x = 1.dp, y = (-1).dp)
                    )
                }
            }
        }

        // Search Bar with Location Pin right badge with fully rounded borders
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = {
                Text(
                    text = t("Find local vibes..."),
                    color = JoyinLightGray,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = FeatherIcons.Search,
                    contentDescription = t("Search"),
                    tint = JoyinLightGray
                )
            },
            trailingIcon = {
                // Red/accent location circle button inside search bar (fully rounded)
                val activeBadgeBg = if (isDarkThemeActiveByOmar) Color(0xFF5E1315) else Color(0xFFFFCDD2)
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(38.dp)
                        .background(if (isNearbyOnlyState) activeBadgeBg else JoyinMediumGray, CircleShape)
                        .clickable { viewModel.toggleNearbyOnly() }
                        .testTag("location_quick_filter_badge"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Compass,
                        contentDescription = "Nearby Location toggle",
                        tint = if (isNearbyOnlyState) Color.Black else JoyinLightGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = JoyinLightGrayBG,
                unfocusedContainerColor = JoyinLightGrayBG,
                disabledContainerColor = JoyinLightGrayBG,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = JoyinCharcoal,
                unfocusedTextColor = JoyinCharcoal
            ),
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("search_bar")
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Horizontally scrolling categories chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category
                Surface(
                    color = if (isSelected) JoyinCharcoal else JoyinLightGrayBG,
                    contentColor = if (isSelected) JoyinWhite else JoyinLightGray,
                    shape = CircleShape,
                    modifier = Modifier
                        .clickable { viewModel.selectCategory(category) }
                        .testTag("category_pill_$category")
                ) {
                    Text(
                        text = t(category),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Vertical List / Scrollable list of events matching Screen 1
        if (events.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = FeatherIcons.Calendar,
                        contentDescription = "No events logo",
                        tint = JoyinLightGray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No gatherings match your criteria.",
                        color = JoyinLightGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Try clearing search queries or select 'All'",
                        color = JoyinLightGray.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(events, key = { it.id }) { event ->
                    EventCardItem(event = event, onClick = { onEventSelected(event) })
                }
            }
        }
    }
}

@Composable
fun getEventGradient(pattern: String?): Brush {
    val colors = when (pattern?.lowercase()?.trim()) {
        "purple" -> listOf(Color(0xFF2E1A47), Color(0xFF140824))
        "teal" -> listOf(Color(0xFF0F363C), Color(0xFF04181B))
        "crimson" -> listOf(Color(0xFF5A1224), Color(0xFF23030A))
        "emerald" -> listOf(Color(0xFF064E3B), Color(0xFF022C22))
        else -> listOf(Color(0xFFFFF1EB), Color(0xFFFFDCD0)) // Default classic peach/cream
    }
    return Brush.linearGradient(colors)
}

@Composable
fun AttendeeAvatarCircle(
    name: String,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFFE57373), // Soft Red
        Color(0xFF81C784), // Soft Green
        Color(0xFF64B5F6), // Soft Blue
        Color(0xFFFFD54F), // Soft Gold
        Color(0xFFBA68C8), // Soft Purple
        Color(0xFF4DB6AC), // Soft Teal
        Color(0xFFFF8A65)  // Soft Orange
    )
    val colorIndex = Math.abs(name.hashCode()) % colors.size
    val backgroundColor = colors[colorIndex]
    
    val initials = name.trim().split("\\s+".toRegex()).let { parts ->
        if (parts.size >= 2) {
            val f = parts[0].take(1)
            val s = parts[1].take(1)
            f + s
        } else if (parts.isNotEmpty() && parts[0].isNotEmpty()) {
            parts[0].take(2)
        } else {
            "?"
        }
    }.uppercase()

    Box(
        modifier = modifier
            .background(backgroundColor, CircleShape)
            .border(1.5.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EventCardItem(
    event: Event,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("event_card_${event.id}")
    ) {
        // Cover container (Screen 1 shows a rounded cream/peach solid image container)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(getEventGradient(event.imageUrl)),
            contentAlignment = Alignment.Center
        ) {
            if (event.imageUrl?.startsWith("http") == true) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = "Event Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                // Decorative subtle aesthetic elements inside card placeholder background
                Icon(
                    imageVector = when {
                        event.tags.contains("Sports", true) -> FeatherIcons.Activity
                        event.tags.contains("Art", true) || event.tags.contains("Calligraphy", true) -> FeatherIcons.Brush
                        else -> FeatherIcons.Users
                    },
                    contentDescription = null,
                    tint = if (event.imageUrl == "cream" || event.imageUrl == null) JoyinAccent.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.size(80.dp)
                )
            }
            
            // Floating audience indicator (e.g. "Males Only") in card
            if (event.audience != "Everyone") {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(JoyinBlack.copy(alpha = 0.65f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = event.audience,
                        color = JoyinWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Event title
        Text(
            text = event.title,
            color = JoyinCharcoal,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.testTag("event_title_label_${event.id}")
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Location and Date split 50-50 in one vertically aligned container
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location split (50%)
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.MapPin,
                    contentDescription = "Location Marker",
                    tint = JoyinAccent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = event.location,
                    color = JoyinLightGray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Date split (50%)
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Calendar,
                    contentDescription = "DateTime",
                    tint = JoyinAccent,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = event.dateTime,
                    color = JoyinLightGray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Price + Overlapping RSVP visitors circles (Screen 1 details)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Price info using formattedPrice extension
            val priceText = event.formattedPrice()
            Text(
                text = priceText,
                color = JoyinCharcoal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Overlapping attendee avatar circles
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                val parsedNames = event.attendeeNames.split(",")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                
                val displayNames = if (parsedNames.isNotEmpty()) {
                    parsedNames
                } else if (event.attendingCount > 0) {
                    listOf("Omar Ramadan", "Youssef Ibrahim", "Nour El-Din", "Farida Mansour", "Amr Hegazi").take(event.attendingCount)
                } else {
                    emptyList()
                }
                
                val circlesCount = minOf(5, displayNames.size)
                if (circlesCount > 0) {
                    Box(
                        modifier = Modifier.height(28.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        for (i in 0 until circlesCount) {
                            val displayName = displayNames[i]
                            AttendeeAvatarCircle(
                                name = displayName,
                                modifier = Modifier
                                    .offset(x = (-i * 12).dp)
                                    .size(26.dp)
                            )
                        }
                    }
                }
                
                if (event.attendingCount > 0) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${event.attendingCount} attending",
                        color = JoyinLightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailScreen(
    event: Event,
    onBack: () -> Unit,
    onJoinToggle: () -> Unit,
    onChatWithHost: () -> Unit,
    viewModel: JoyinViewModel? = null
) {
    var showMapBox by remember { mutableStateOf(false) }
    val mapContext = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JoyinWhite)
            .testTag("event_details_overlay")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            // Header: Back, Title: "Event Details", More options
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("details_back_btn")) {
                    Icon(
                        imageVector = FeatherIcons.ChevronLeft,
                        contentDescription = "Go back",
                        tint = JoyinCharcoal,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "Event Details",
                    color = JoyinCharcoal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = FeatherIcons.MoreVertical,
                        contentDescription = "Options",
                        tint = JoyinCharcoal
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // Massive image header (Screen 2 cream/pinkish cover placeholder)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(getEventGradient(event.imageUrl)),
                    contentAlignment = Alignment.Center
                ) {
                    if (event.imageUrl?.startsWith("http") == true) {
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = "Event Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = FeatherIcons.Calendar,
                            contentDescription = null,
                            tint = if (event.imageUrl == "cream" || event.imageUrl == null) JoyinAccent.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Event Title and Organizer Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Host profile placeholder (filled gray circle)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(JoyinLightGrayBG, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = event.hostName.take(1).uppercase(),
                            color = JoyinCharcoal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = event.title,
                            color = JoyinCharcoal,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = event.hostName,
                            color = JoyinLightGray,
                            fontSize = 14.sp
                        )
                    }

                    // Floating quick chat bubble to direct message host
                    IconButton(
                        onClick = onChatWithHost,
                        modifier = Modifier
                            .background(JoyinLightGrayBG, CircleShape)
                            .testTag("chat_with_host_button")
                    ) {
                        Icon(
                            imageVector = FeatherIcons.MessageSquare,
                            contentDescription = "Message host",
                            tint = JoyinAccent
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Meta location and calendar rows (Unified container)

                Surface(
                    color = JoyinLightGrayBG,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Location Row with open-in-maps arrow button
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = FeatherIcons.MapPin,
                                contentDescription = "Location pin",
                                tint = JoyinAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = event.location,
                                color = JoyinCharcoal,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            // Arrow top-right icon to open map popup
                            IconButton(
                                onClick = { showMapBox = true },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = "Open map",
                                    tint = JoyinAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Calendar / Date Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Calendar,
                                contentDescription = "Calendar slot",
                                tint = JoyinAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = event.dateTime,
                                color = JoyinCharcoal,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Floating map popup box rendered here but will be layered by outer Box

                Spacer(modifier = Modifier.height(14.dp))

                // Price and audience row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val priceText = event.formattedPrice()
                    Text(
                        text = priceText,
                        color = JoyinCharcoal,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Surface(
                        color = JoyinLightGrayBG,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = event.audience,
                            color = JoyinCharcoal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Live attendee metadata + Overlapping initials circles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val parsedNames = event.attendeeNames.split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                    
                    val displayNames = if (parsedNames.isNotEmpty()) {
                        parsedNames
                    } else if (event.attendingCount > 0) {
                        listOf("Omar Ramadan", "Youssef Ibrahim", "Nour El-Din", "Farida Mansour", "Amr Hegazi").take(event.attendingCount)
                    } else {
                        emptyList()
                    }
                    val circlesCount = minOf(5, displayNames.size)

                    Text(
                        text = "${event.attendingCount} Attending",
                        color = JoyinCharcoal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (circlesCount > 0) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.height(26.dp)) {
                            for (i in 0 until circlesCount) {
                                val displayName = displayNames[i]
                                AttendeeAvatarCircle(
                                    name = displayName,
                                    modifier = Modifier
                                        .offset(x = (i * 12).dp)
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }

                // If user is organizer: show joined/target count badge
                val loggedInUserName = viewModel?.userDisplayName?.collectAsStateWithLifecycle()?.value ?: ""
                if (loggedInUserName.isNotBlank() && event.hostName.equals(loggedInUserName, ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = JoyinAccent.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(FeatherIcons.Users, contentDescription = null, tint = JoyinAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${event.attendingCount}/${event.maxCapacity} joined",
                                color = JoyinAccent,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // About Event description
                Text(
                    text = t("About Event"),
                    color = JoyinCharcoal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = event.description,
                    color = JoyinLightGray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Interactive OpenStreetMap maps and navigation
                OpenStreetMapSection(locationName = event.location)

                Spacer(modifier = Modifier.height(18.dp))

                // Tags section
                Text(
                    text = t("Tags"),
                    color = JoyinCharcoal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Horizontally wrapped custom flow grid chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    event.tags.split(",").forEach { tag ->
                        if (tag.isNotBlank()) {
                            Surface(
                                color = JoyinLightGrayBG,
                                shape = CircleShape
                            ) {
                                Text(
                                    text = t(tag.trim()),
                                    color = JoyinCharcoal,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Join Button
                Button(
                    onClick = onJoinToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (event.isJoined) JoyinSelectedRedBg else JoyinAccent,
                        contentColor = if (event.isJoined) JoyinAccent else JoyinWhite
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("join_action_button")
                ) {
                    Text(
                        text = if (event.isJoined) "Leave Event" else "Join",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // FloatingMapBox overlaid at top level so it covers the whole screen
        if (showMapBox) {
            FloatingMapBox(
                locationName = event.location,
                onDismiss = { showMapBox = false },
                onOpenInMaps = {
                    val query = Uri.encode(event.location)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$query"))
                    mapContext.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun FloatingMapBox(
    locationName: String,
    onDismiss: () -> Unit,
    onOpenInMaps: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clickable { /* consume clicks */ },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Location",
                        color = JoyinCharcoal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .background(JoyinMediumGray, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close map",
                            tint = JoyinCharcoal,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location name row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = FeatherIcons.MapPin,
                        contentDescription = null,
                        tint = JoyinAccent,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = locationName,
                        color = JoyinCharcoal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Open in Maps button — fully rounded accent
                Button(
                    onClick = {
                        onOpenInMaps()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = JoyinAccent),
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Open in Maps",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OpenStreetMapSection(
    locationName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isSimulatorActive by remember { mutableStateOf(false) }
    
    // Resolve location to lat and lon
    val coords = when {
        locationName.contains("Heliopolis", ignoreCase = true) -> Pair(30.0971, 31.3262)
        locationName.contains("Maadi", ignoreCase = true) -> Pair(29.9602, 31.2569)
        locationName.contains("El Salam", ignoreCase = true) -> Pair(30.1517, 31.4286)
        locationName.contains("Cairo", ignoreCase = true) -> Pair(30.0444, 31.2357)
        else -> Pair(30.0444, 31.2357)
    }
    
    val lat = coords.first
    val lon = coords.second
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(JoyinLightGrayBG, RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("osm_maps_container")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = FeatherIcons.Map,
                    contentDescription = null,
                    tint = JoyinAccent,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = t("OpenStreetMap View"),
                    color = JoyinCharcoal,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Surface(
                color = JoyinWhite,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = t("OSM Active"),
                    color = JoyinAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        // Android WebView showing OSM map iframe with a marker pin!
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, JoyinBorderGray, RoundedCornerShape(12.dp))
                .testTag("osm_map_webview_frame")
        ) {
            val embedUrl = "https://www.openstreetmap.org/export/embed.html?bbox=${lon - 0.005}%2C${lat - 0.005}%2C${lon + 0.005}%2C${lat + 0.005}&layer=mapnik&marker=${lat}%2C${lon}"
            
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()
                        loadUrl(embedUrl)
                    }
                },
                update = { webView ->
                    webView.loadUrl(embedUrl)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Open Map Intent
            Button(
                onClick = {
                    val geoUri = Uri.parse("geo:$lat,$lon?q=$lat,$lon($locationName)")
                    val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.openstreetmap.org/directions?route=%3B$lat%2C$lon"))
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        try {
                            context.startActivity(webIntent)
                        } catch (ex: Exception) {
                            // Handler fallback
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = JoyinCharcoal,
                    contentColor = JoyinWhite
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
                    .testTag("btn_navigate_on_osm")
            ) {
                Icon(
                    imageVector = FeatherIcons.Compass,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = t("Navigate"),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Start Interactive Navigation in UI Simulation
            Button(
                onClick = { isSimulatorActive = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = JoyinAccent,
                    contentColor = JoyinWhite
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1.2f)
                    .height(42.dp)
                    .testTag("btn_start_osm_sim")
            ) {
                Icon(
                    imageVector = FeatherIcons.Compass,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = t("Start OSM Navigation"),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    
    // Interactive Navigation Simulator Dialog overlay (Beautiful, polished)
    if (isSimulatorActive) {
        OsmNavigationSimulatorDialog(
            locationName = locationName,
            lat = lat,
            lon = lon,
            onDismiss = { isSimulatorActive = false }
        )
    }
}

@Composable
fun OsmNavigationSimulatorDialog(
    locationName: String,
    lat: Double,
    lon: Double,
    onDismiss: () -> Unit
) {
    var simStep by remember { mutableStateOf(0) }
    var currentSpeed by remember { mutableStateOf(35) }
    var metersRemaining by remember { mutableStateOf(850) }
    
    // Automatically alter speed and decrease meters remaining to simulate actual movement
    LaunchedEffect(simStep) {
        when (simStep) {
            0 -> { currentSpeed = 35; metersRemaining = 850 }
            1 -> { currentSpeed = 46; metersRemaining = 420 }
            2 -> { currentSpeed = 0; metersRemaining = 0 }
        }
    }
    
    val stepsList = listOf(
        "Head North-East on Al-Nasr road towards the event.",
        "Turn right using the residential block pathway.",
        "You have arrived safely using OSM instructions!"
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = FeatherIcons.Compass,
                    contentDescription = null,
                    tint = JoyinAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = t("Navigation Simulator"),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = JoyinCharcoal
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("osm_simulator_content")
            ) {
                Text(
                    text = "${t("OSM Navigation Interface")} -> ${t(locationName)}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = JoyinLightGray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Dashboard Indicators: Speed, Distance, ETA
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = t("Speed"), fontSize = 11.sp, color = JoyinLightGray)
                        Text(
                            text = if (metersRemaining > 0) "${currentSpeed} km/h" else "0 km/h",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = JoyinCharcoal
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = t("Distance"), fontSize = 11.sp, color = JoyinLightGray)
                        Text(
                            text = if (metersRemaining > 0) "${metersRemaining} m" else t("Arrived"),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (metersRemaining == 0) JoyinAccent else JoyinCharcoal
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = t("ETA"), fontSize = 11.sp, color = JoyinLightGray)
                        Text(
                            text = if (metersRemaining > 0) "${(metersRemaining / 100) + 1} min" else "--",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = JoyinCharcoal
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Visual Indicator Compass direction circle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(60.dp)) {
                        // Draw navigation outer ring
                        drawCircle(
                            color = JoyinLightGrayBG,
                            radius = size.minDimension / 2
                        )
                        // Simple pointing line in Compose
                        val center = Offset(size.width / 2, size.height / 2)
                        if (metersRemaining == 0) {
                            // Arrived
                            drawCircle(
                                color = Color.Green,
                                radius = 8.dp.toPx(),
                                center = center
                            )
                        } else if (simStep == 1) {
                            // Turn right line
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(-12.dp.toPx(), 0f),
                                end = center + Offset(12.dp.toPx(), 0f),
                                strokeWidth = 4.dp.toPx()
                            )
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(12.dp.toPx(), 0f),
                                end = center + Offset(6.dp.toPx(), -6.dp.toPx()),
                                strokeWidth = 4.dp.toPx()
                            )
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(12.dp.toPx(), 0f),
                                end = center + Offset(6.dp.toPx(), 6.dp.toPx()),
                                strokeWidth = 4.dp.toPx()
                            )
                        } else {
                            // Go straight arrow
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(0f, 12.dp.toPx()),
                                end = center + Offset(0f, -12.dp.toPx()),
                                strokeWidth = 4.dp.toPx()
                            )
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(0f, -12.dp.toPx()),
                                end = center + Offset(-6.dp.toPx(), -5.dp.toPx()),
                                strokeWidth = 4.dp.toPx()
                            )
                            drawLine(
                                color = JoyinAccent,
                                start = center + Offset(0f, -12.dp.toPx()),
                                end = center + Offset(6.dp.toPx(), -5.dp.toPx()),
                                strokeWidth = 4.dp.toPx()
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Step details instructions
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (metersRemaining == 0) JoyinSelectedRedBg else JoyinWhite
                    ),
                    border = BorderStroke(1.dp, JoyinBorderGray),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "${t("Step")} ${simStep + 1}:",
                            color = JoyinAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = t(stepsList[simStep]),
                            color = JoyinCharcoal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (simStep < 2) {
                Button(
                    onClick = { simStep++ },
                    colors = ButtonDefaults.buttonColors(containerColor = JoyinAccent)
                ) {
                    Text(text = t("Next Step"), fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = JoyinCharcoal)
                ) {
                    Text(text = t("Close OSM Simulator"), fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = t("End Navigation"), color = JoyinLightGray)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEventsScreen(
    joinedEvents: List<Event>,
    onEventSelected: (Event) -> Unit,
    viewModel: JoyinViewModel? = null
) {
    // Use the real current day of month as the default active day
    val todayDayOfMonth = remember {
        java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH).toString()
    }
    var selectedDay by remember { mutableStateOf(todayDayOfMonth) }

    // Build a 7-day sliding window starting from today
    val daysList = remember {
        val cal = java.util.Calendar.getInstance()
        val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        (0..6).map {
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
            val w = dayNames[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1]
            cal.add(java.util.Calendar.DAY_OF_MONTH, 1)
            DayItem(d, w)
        }
    }

    // Sort joined events chronologically (closest first)
    val sortedJoined = remember(joinedEvents) {
        joinedEvents.sortedBy { it.dateDay.toIntOrNull() ?: 0 }
    }

    val filteredEventsForDay = sortedJoined.filter { it.dateDay == selectedDay }

    val loggedInName = viewModel?.userDisplayName?.collectAsStateWithLifecycle()?.value ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // App bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.size(36.dp))

            Text(
                text = t("My Events"),
                color = JoyinCharcoal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {}) {
                Icon(
                    imageVector = FeatherIcons.MoreVertical,
                    contentDescription = t("Options"),
                    tint = JoyinCharcoal
                )
            }
        }

        // Day slider — vertical half-rounded pill cards, horizontally scrollable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            daysList.forEach { day ->
                val isSelected = selectedDay == day.day
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(
                            if (isSelected) JoyinAccent else JoyinLightGrayBG
                        )
                        .clickable { selectedDay = day.day }
                        .testTag("calendar_day_${day.day}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = day.day,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else JoyinCharcoal
                        )
                        Text(
                            text = day.weekday,
                            fontSize = 11.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else JoyinLightGray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredEventsForDay.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = FeatherIcons.Calendar,
                        contentDescription = t("Empty calendar"),
                        tint = JoyinLightGray,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = t("No events scheduled for Day") + " $selectedDay.",
                        color = JoyinLightGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = t("Toggle other dates or join an event in search."),
                        color = JoyinLightGray.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(filteredEventsForDay, key = { it.id }) { event ->
                    MyEventRowItem(
                        event = event,
                        loggedInName = loggedInName,
                        onClick = { onEventSelected(event) }
                    )
                }
            }
        }
    }
}

data class DayItem(
    val day: String,
    val weekday: String
)

@Composable
fun MyEventRowItem(
    event: Event,
    loggedInName: String = "",
    onClick: () -> Unit
) {
    val isOrganizer = loggedInName.isNotBlank() && event.hostName.equals(loggedInName, ignoreCase = true)
    val isFree = event.price == 0.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(JoyinLightGrayBG, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
            .testTag("my_event_row_${event.id}")
    ) {
        // Cover image — half-rounded top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(getEventGradient(event.imageUrl)),
            contentAlignment = Alignment.Center
        ) {
            if (event.imageUrl?.startsWith("http") == true) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = "Event Cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = FeatherIcons.Calendar,
                    contentDescription = null,
                    tint = if (event.imageUrl == "cream" || event.imageUrl == null)
                        JoyinAccent.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Title + time
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
            Text(
                text = event.title,
                color = JoyinCharcoal,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = JoyinAccent,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = event.dateTime,
                    color = JoyinLightGray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Organizer: joined/target count
            if (isOrganizer) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = JoyinAccent.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${event.attendingCount}/${event.maxCapacity} joined",
                        color = JoyinAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            // Non-organizer + free event: safety notice
            if (!isOrganizer && isFree) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = FeatherIcons.AlertTriangle,
                        contentDescription = null,
                        tint = JoyinAccent,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "This event is free, if anyone charged you please report.",
                        color = JoyinLightGray,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun InboxListScreen(
    events: List<Event>,
    onChatSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // App header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Inbox Messages",
                color = JoyinCharcoal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // List of organizers they can start/has convo with
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(events) { event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                        .clickable { onChatSelected(event.id) }
                        .padding(12.dp)
                        .testTag("inbox_item_${event.id}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(JoyinMediumGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = event.hostName.take(1).uppercase(),
                            color = JoyinCharcoal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = event.hostName,
                            color = JoyinCharcoal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Organizer of ${event.title}",
                            color = JoyinLightGray,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        imageVector = FeatherIcons.MessageSquare,
                        contentDescription = "Open Chat",
                        tint = JoyinAccent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatDetailScreen(
    event: Event,
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onBack: () -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JoyinWhite)
            .testTag("chat_detail_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("chat_back_btn")) {
                    Icon(
                        imageVector = FeatherIcons.ChevronLeft,
                        contentDescription = "Back",
                        tint = JoyinCharcoal,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "Inbox",
                    color = JoyinCharcoal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = FeatherIcons.MoreVertical,
                        contentDescription = "Options",
                        tint = JoyinCharcoal
                    )
                }
            }

            // Big Avatar Detail Row under header (Screen 4 Layout)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Grey circle avatar
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(JoyinLightGrayBG, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = event.hostName.take(1).uppercase(),
                        color = JoyinCharcoal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                // Ahmed Mohamed
                Text(
                    text = event.hostName,
                    color = JoyinCharcoal,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                // Organizer of Workshop
                Text(
                    text = "Organizer Of Workshop",
                    color = JoyinLightGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Messages chat logs
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(messages) { message ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        // User avatar indicator on the left for Host messages (Screen 4 Details)
                        if (!message.isFromMe) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(JoyinWhite, CircleShape)
                                    .border(1.dp, JoyinLightGray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = message.senderName.take(1),
                                    color = JoyinBlack,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        // Bubbles
                        if (message.isSpecialLink) {
                            // Link Box matching Screen 4 Paperclip Zoom Link Design
                            Column(
                                modifier = Modifier
                                    .widthIn(max = 280.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(JoyinSelectedRedBg)
                                    .border(1.dp, JoyinAccent.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = FeatherIcons.Link,
                                        contentDescription = "Attachment link",
                                        tint = JoyinAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = message.content.substringBefore("\n"),
                                        color = JoyinCharcoal,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = message.content.substringAfter("\n"),
                                    color = JoyinCharcoal.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        } else {
                            // Text Bubble
                            val bubbleColor = when {
                                message.isFromMe -> JoyinAccent
                                // Simulated Brownish Red Notification bub
                                message.content.contains("Your Event") -> JoyinSelectedRedBg
                                else -> JoyinBubbleGray
                            }
                            
                            val textFontStyle = if (message.content.contains("Your Event")) FontStyle.Italic else FontStyle.Normal

                            Box(
                                modifier = Modifier
                                    .widthIn(max = 260.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(bubbleColor)
                                    .padding(vertical = 10.dp, horizontal = 14.dp)
                            ) {
                                Text(
                                    text = message.content,
                                    color = if (bubbleColor == JoyinAccent) JoyinWhite else JoyinCharcoal,
                                    fontSize = 14.sp,
                                    fontStyle = textFontStyle
                                )
                            }
                        }
                    }
                }
            }

            // Chat input bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = textState,
                    onValueChange = { textState = it },
                    placeholder = {
                        Text(
                            text = "Send a message",
                            color = JoyinLightGray,
                            fontSize = 14.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = JoyinBubbleGray,
                        unfocusedContainerColor = JoyinBubbleGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = JoyinWhite,
                        unfocusedTextColor = JoyinWhite
                    ),
                    shape = RoundedCornerShape(24.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (textState.isNotBlank()) {
                                onSendMessage(textState)
                                textState = ""
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("chat_input_field")
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Send icon button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(JoyinAccent, CircleShape)
                        .clickable {
                            if (textState.isNotBlank()) {
                                onSendMessage(textState)
                                textState = ""
                                focusManager.clearFocus()
                            }
                        }
                        .testTag("chat_send_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Send,
                        contentDescription = "Send Message",
                        tint = JoyinWhite,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CreateScreen(
    viewModel: JoyinViewModel
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    if (!isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                border = BorderStroke(1.dp, JoyinBorderGray)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = FeatherIcons.Plus,
                        contentDescription = null,
                        tint = JoyinAccent,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = t("List Neighborhood Event"),
                        color = JoyinCharcoal,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = t("Please sign in to unlock custom preferences, send secure messages, or create neighborhood events."),
                        color = JoyinLightGray,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.selectTab("Profile") },
                        colors = ButtonDefaults.buttonColors(containerColor = JoyinAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text(text = t("Sign in to Joyin"), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        return
    }

    var title by remember { mutableStateOf("") }
    var hostName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    // Type selector
    var eventType by remember { mutableStateOf("Onsite") } // "Onsite" or "Online"
    var onlineLink by remember { mutableStateOf("") }
    var onsiteLocation by remember { mutableStateOf("") }
    var isLocatingGps by remember { mutableStateOf(false) }

    // Start & End Calendar date selection states
    // Current date is May 26, 2026.
    var startDay by remember { mutableStateOf(26) }
    var startMonth by remember { mutableStateOf(5) } // 5 for May, 6 for June
    var endDay by remember { mutableStateOf(26) }
    var endMonth by remember { mutableStateOf(5) }

    var isSelectingEndDate by remember { mutableStateOf(false) } // Select start or end date in calendar
    var repeatInterval by remember { mutableStateOf("Single Event") } // "Single Event", "Daily", "Weekly", "Monthly"

    // Time Selection
    var startHour by remember { mutableStateOf(6) }
    var startMinute by remember { mutableStateOf(0) }
    var startAmPm by remember { mutableStateOf("PM") }

    var priceStr by remember { mutableStateOf("") }
    var maxCapacityStr by remember { mutableStateOf("100") }
    var audience by remember { mutableStateOf("Everyone") } // "Everyone", "Males Only", "Females Only"
    var tags by remember { mutableStateOf("") }
    var selectedImageCover by remember { mutableStateOf("cream") }

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fineGranted || coarseGranted) {
            isLocatingGps = true
        } else {
            Toast.makeText(context, "Location permission is required for GPS detection", Toast.LENGTH_SHORT).show()
        }
    }

    // Real GPS locate
    LaunchedEffect(isLocatingGps) {
        if (isLocatingGps) {
            val finePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            val coarsePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            if (finePermission || coarsePermission) {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                if (locationManager != null) {
                    var location: Location? = null
                    try {
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        }
                        if (location == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        }
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }

                    if (location != null) {
                        val lat = String.format("%.4f", location.latitude)
                        val lon = String.format("%.4f", location.longitude)
                        var addressText = "GPS: $lat, $lon"
                        try {
                            val geocoder = Geocoder(context, java.util.Locale.getDefault())
                            // geocoder check
                            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if (!addresses.isNullOrEmpty()) {
                                val address = addresses[0]
                                val subLocality = address.subLocality ?: address.locality
                                val city = address.adminArea ?: address.countryName ?: "Cairo"
                                val street = address.thoroughfare
                                addressText = if (street != null && subLocality != null) {
                                    "$street, $subLocality, $city"
                                } else if (subLocality != null) {
                                    "$subLocality, $city"
                                } else {
                                    "$city ($lat, $lon)"
                                }
                            } else {
                                addressText = "GPS Marker ($lat, $lon)"
                            }
                        } catch (e: Exception) {
                            addressText = "GPS: $lat, $lon"
                        }
                        onsiteLocation = addressText
                    } else {
                        // Safe realistic fallback if getLastKnownLocation is null, but we attempt a mock representing user's coordinate sector
                        onsiteLocation = "Maadi Sector (30.0444, 31.2357)"
                    }
                } else {
                    onsiteLocation = "Maadi GPS (30.0444, 31.2357)"
                }
                isLocatingGps = false
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                isLocatingGps = false
            }
        }
    }

    // Past Date and Time Validation logic (local UTC time is May 26, 2026, 00:50 AM)
    val isPastSelected = remember(startDay, startMonth, startHour, startMinute, startAmPm) {
        if (startMonth < 5) {
            true
        } else if (startMonth == 5 && startDay < 26) {
            true
        } else if (startMonth == 5 && startDay == 26) {
            val startHour24 = if (startAmPm == "PM" && startHour != 12) startHour + 12 
                              else if (startAmPm == "AM" && startHour == 12) 0 
                              else startHour
            // Compare vs 00:50
            if (startHour24 < 0) true
            else if (startHour24 == 0) startMinute < 50
            else false
        } else {
            false
        }
    }

    val isEndBeforeStart = remember(startDay, startMonth, endDay, endMonth) {
        if (endMonth < startMonth) {
            true
        } else if (endMonth == startMonth && endDay < startDay) {
            true
        } else {
            false
        }
    }

    // Form validation check
    val isFormValid = title.isNotBlank() && 
                      hostName.isNotBlank() && 
                      (if (eventType == "Online") onlineLink.isNotBlank() else onsiteLocation.isNotBlank()) &&
                      !isPastSelected && 
                      !isEndBeforeStart

    // Dynamic generated dateTime summary matching rendering formats (with repeat capability)
    val startMonthStr = if (startMonth == 5) "May" else "June"
    val endMonthStr = if (endMonth == 5) "May" else "June"
    val formattedTime = String.format("%d:%02d %s", startHour, startMinute, startAmPm)
    val dateTimeSummary = remember(startDay, startMonth, endDay, endMonth, formattedTime, repeatInterval) {
        if (startDay == endDay && startMonth == endMonth) {
            "$startMonthStr $startDay - $formattedTime ($repeatInterval)"
        } else {
            "$startMonthStr $startDay to $endMonthStr $endDay - $formattedTime ($repeatInterval)"
        }
    }

    // Days of Week calculation for Home item
    val computedWeekday = remember(startDay, startMonth) {
        if (startMonth == 5) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[(startDay + 3) % 7]
        } else {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[(startDay - 1) % 7]
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = t("List Block Event"),
                color = JoyinCharcoal,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = t("Spread word of local gatherings or workshops in your neighborhood block."),
            color = JoyinLightGray,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 1- Image input (Event Cover Visual Block at the top)
        Text(t("Event Cover Visual Block *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Text(t("Choose an abstract premium gradient accent style for your visual."), color = JoyinLightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
        
        val covers = listOf(
            "cream" to t("Classic Peach"),
            "crimson" to t("Sunset Crimson"),
            "purple" to t("Midnight Purple"),
            "teal" to t("Ocean Teal"),
            "emerald" to t("Tech Emerald")
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            covers.forEach { (key, label) ->
                val isSelected = selectedImageCover == key
                Surface(
                    color = if (isSelected) JoyinAccent else JoyinLightGrayBG,
                    contentColor = if (isSelected) Color.White else JoyinCharcoal,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSelected) JoyinAccent else JoyinBorderGray),
                    modifier = Modifier.clickable { selectedImageCover = key }
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(getEventGradient(selectedImageCover)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = t("Banner Preview"),
                color = if (selectedImageCover == "cream") JoyinAccent.copy(alpha = 0.60f) else Color.White.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // 2- Event Title + Host
        Text(t("Event Title *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text(t("e.g. Arabic Calligraphy Class"), color = JoyinLightGray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = JoyinLightGrayBG,
                unfocusedContainerColor = JoyinLightGrayBG,
                focusedIndicatorColor = JoyinAccent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = JoyinCharcoal,
                unfocusedTextColor = JoyinCharcoal
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp)
                .testTag("form_title_input")
        )

        Text(t("Host Name *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = hostName,
            onValueChange = { hostName = it },
            placeholder = { Text(t("e.g. Abdelrahman"), color = JoyinLightGray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = JoyinLightGrayBG,
                unfocusedContainerColor = JoyinLightGrayBG,
                focusedIndicatorColor = JoyinAccent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = JoyinCharcoal,
                unfocusedTextColor = JoyinCharcoal
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp)
                .testTag("form_host_input")
        )

        // 3- Description
        Text(t("Description *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text(t("Provide details of times, activities, block-rules..."), color = JoyinLightGray) },
            minLines = 3,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = JoyinLightGrayBG,
                unfocusedContainerColor = JoyinLightGrayBG,
                focusedIndicatorColor = JoyinAccent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = JoyinCharcoal,
                unfocusedTextColor = JoyinCharcoal
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp)
        )

        // 4- Event venue mode
        Text(t("Event Venue Mode *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp)
        ) {
            listOf("Onsite", "Online").forEach { type ->
                val isSelected = eventType == type
                Surface(
                    color = if (isSelected) JoyinAccent else JoyinLightGrayBG,
                    contentColor = if (isSelected) Color.White else JoyinCharcoal,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                        .clickable { eventType = type }
                ) {
                    Text(
                        text = t(type),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
        }

        // 5- Link if online , location if onsite
        if (eventType == "Online") {
            Text(t("Online Meeting Link *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            TextField(
                value = onlineLink,
                onValueChange = { onlineLink = it },
                placeholder = { Text(t("e.g. https://meet.google.com/abc-xyz"), color = JoyinLightGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = JoyinLightGrayBG,
                    unfocusedContainerColor = JoyinLightGrayBG,
                    focusedIndicatorColor = JoyinAccent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = JoyinCharcoal,
                    unfocusedTextColor = JoyinCharcoal
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp)
            )
        } else {
            Text(t("Onsite Address (OSM Map Center) *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = onsiteLocation,
                    onValueChange = { onsiteLocation = it },
                    placeholder = { Text(t("e.g. Heliopolis, Cairo"), color = JoyinLightGray) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = JoyinLightGrayBG,
                        unfocusedContainerColor = JoyinLightGrayBG,
                        focusedIndicatorColor = JoyinAccent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = JoyinCharcoal,
                        unfocusedTextColor = JoyinCharcoal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        if (isLocatingGps) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = JoyinAccent)
                        }
                    }
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                IconButton(
                    onClick = { isLocatingGps = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(JoyinAccent, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = FeatherIcons.Compass,
                        contentDescription = "GPS Detection",
                        tint = JoyinCharcoal
                    )
                }
            }
        }

        // Date, Calendar & Time selectors
        Text(t("Schedule Event Interval (Calendar-Based) *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(t("Current Date is May 26, 2026. Prior dates are blocked."), color = JoyinLightGray, fontSize = 11.sp, modifier = Modifier.padding(bottom = 8.dp))

        // Month Selector
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(5 to t("May 2026"), 6 to t("June 2026")).forEach { (mNum, label) ->
                val isSelected = if (isSelectingEndDate) endMonth == mNum else startMonth == mNum
                Surface(
                    color = if (isSelected) JoyinCharcoal else JoyinLightGrayBG,
                    contentColor = if (isSelected) Color.White else JoyinLightGray,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).clickable {
                        if (isSelectingEndDate) {
                            endMonth = mNum
                        } else {
                            startMonth = mNum
                        }
                    }
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        // Horizontal Start, End, and Repeat selectors in one container horizontally!
        var isRepeatDropdownExpanded by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Start Date Trigger
            Box(
                modifier = Modifier
                    .weight(1.1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (!isSelectingEndDate) JoyinAccent else Color.Transparent)
                    .clickable { isSelectingEndDate = false }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(t("Start Date"), color = if (!isSelectingEndDate) Color.White else JoyinLightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("$startMonthStr $startDay", color = if (!isSelectingEndDate) Color.White else JoyinCharcoal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            // End Date Trigger
            Box(
                modifier = Modifier
                    .weight(1.1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelectingEndDate) JoyinAccent else Color.Transparent)
                    .clickable { isSelectingEndDate = true }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(t("End Date"), color = if (isSelectingEndDate) Color.White else JoyinLightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("$endMonthStr $endDay", color = if (isSelectingEndDate) Color.White else JoyinCharcoal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Repeat routine selector
            Box(
                modifier = Modifier
                    .weight(1.3f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Transparent)
                    .clickable { isRepeatDropdownExpanded = true }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(t("Repeat"), color = JoyinLightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(t(repeatInterval), color = JoyinAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = JoyinAccent, modifier = Modifier.size(12.dp))
                    }
                    
                    DropdownMenu(
                        expanded = isRepeatDropdownExpanded,
                        onDismissRequest = { isRepeatDropdownExpanded = false }
                    ) {
                        listOf("Single Event", "Daily", "Weekly", "Monthly").forEach { interval ->
                            DropdownMenuItem(
                                text = { Text(t(interval)) },
                                onClick = {
                                    repeatInterval = interval
                                    isRepeatDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Calendar Grid
        val renderMonth = if (isSelectingEndDate) endMonth else startMonth
        val renderChosenDay = if (isSelectingEndDate) endDay else startDay
        val daysInMonth = if (renderMonth == 5) 31 else 30
        val emptySlots = if (renderMonth == 5) 4 else 0
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(JoyinLightGrayBG, RoundedCornerShape(14.dp))
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach {
                    Text(text = it, color = JoyinLightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                }
            }
            
            val totalCells = daysInMonth + emptySlots
            val rowsCount = (totalCells + 6) / 7
            for (r in 0 until rowsCount) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    for (c in 0 until 7) {
                        val cellIndex = r * 7 + c
                        val dayNumber = cellIndex - emptySlots + 1
                        val isValidDay = cellIndex >= emptySlots && dayNumber <= daysInMonth
                        
                        if (isValidDay) {
                            val isPast = (renderMonth < 5) || (renderMonth == 5 && dayNumber < 26)
                            val isChosen = renderChosenDay == dayNumber
                            
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(3.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = if (isChosen) JoyinAccent 
                                                else if (isPast) Color.Transparent 
                                                else JoyinWhite
                                    )
                                    .clickable(enabled = !isPast) {
                                        if (isSelectingEndDate) {
                                            endDay = dayNumber
                                        } else {
                                            startDay = dayNumber
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = if (isChosen) Color.White 
                                            else if (isPast) JoyinLightGray.copy(alpha = 0.35f) 
                                            else JoyinCharcoal,
                                    fontSize = 11.sp,
                                    fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        } else {
                            Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Time Selection Dial layout
        Text(t("Event Start Time"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hours scroll
            Row(
                modifier = Modifier
                    .weight(1.5f)
                    .horizontalScroll(rememberScrollState())
                    .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                (1..12).forEach { h ->
                    val isS = startHour == h
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(if (isS) JoyinAccent else Color.Transparent)
                            .clickable { startHour = h },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(h.toString(), fontSize = 12.sp, color = if (isS) Color.White else JoyinCharcoal, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Minutes scroll
            Row(
                modifier = Modifier
                    .weight(1.2f)
                    .horizontalScroll(rememberScrollState())
                    .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf(0, 15, 30, 45).forEach { m ->
                    val isS = startMinute == m
                    Box(
                        modifier = Modifier
                            .width(38.dp)
                            .height(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isS) JoyinAccent else Color.Transparent)
                            .clickable { startMinute = m },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(String.format("%02d", m), fontSize = 12.sp, color = if (isS) Color.White else JoyinCharcoal, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // AM/PM Switch
            Row(
                modifier = Modifier
                    .background(JoyinLightGrayBG, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                listOf("AM", "PM").forEach { p ->
                    val isS = startAmPm == p
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .width(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isS) JoyinAccent else Color.Transparent)
                            .clickable { startAmPm = p },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(t(p), fontSize = 12.sp, color = if (isS) Color.White else JoyinCharcoal, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Live Date Summary Box
        Card(
            colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
            border = BorderStroke(1.dp, JoyinBorderGray),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(t("Structured Event Schedule Summary:"), color = JoyinLightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(dateTimeSummary, color = JoyinAccent, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
            }
        }

        // 6- cost + attendees 'one container'
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(t("Entry Fee / Cost ($) *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                TextField(
                    value = priceStr,
                    onValueChange = { priceStr = it },
                    placeholder = { Text(t("0.00 for Free"), color = JoyinLightGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = JoyinLightGrayBG,
                        unfocusedContainerColor = JoyinLightGrayBG,
                        focusedIndicatorColor = JoyinAccent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = JoyinCharcoal,
                        unfocusedTextColor = JoyinCharcoal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .testTag("form_price_input")
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(t("Max Attendees Limit *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                TextField(
                    value = maxCapacityStr,
                    onValueChange = { maxCapacityStr = it },
                    placeholder = { Text(t("e.g. 50"), color = JoyinLightGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = JoyinLightGrayBG,
                        unfocusedContainerColor = JoyinLightGrayBG,
                        focusedIndicatorColor = JoyinAccent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = JoyinCharcoal,
                        unfocusedTextColor = JoyinCharcoal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .testTag("form_max_capacity_input")
                )
            }
        }

        // Float / warn banners right below Cost + Attendees
        val priceVal = priceStr.toDoubleOrNull() ?: 0.0
        val isFree = priceVal == 0.0
        
        if (isFree) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x27EE4B2B)),
                border = BorderStroke(1.dp, Color(0xFFFF4D4D)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(FeatherIcons.AlertTriangle, contentDescription = "Warning", tint = Color(0xFFEE4B2B))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = t("You've set this event for free, if you charged anyone you'll be banned."),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x221BE31B)),
                border = BorderStroke(1.dp, Color(0xFF1BE31B)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(FeatherIcons.CheckCircle, contentDescription = "Income Notification", tint = Color(0xFF1BE31B))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = t("You'll receive + 80% of price (we take 20% from every participant who joins, as there are no payment gateways implemented)."),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 7- Gender Target Audience Segment
        Text(t("Target Audience Segment *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp)
        ) {
            listOf("Everyone" to t("Everyone"), "Males Only" to t("Male Only"), "Females Only" to t("Female Only")).forEach { (key, label) ->
                val isSelected = audience == key
                Surface(
                    color = if (isSelected) JoyinAccent else JoyinLightGrayBG,
                    contentColor = if (isSelected) Color.White else JoyinCharcoal,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                        .clickable { audience = key }
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        // 8- Tags Comma Separated and database toggles
        Text(t("Tags (Comma Separated) *"), color = JoyinCharcoal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = tags,
            onValueChange = { tags = it },
            placeholder = { Text(t("Cairo, Sports, Race, Running"), color = JoyinLightGray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = JoyinLightGrayBG,
                unfocusedContainerColor = JoyinLightGrayBG,
                focusedIndicatorColor = JoyinAccent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = JoyinCharcoal,
                unfocusedTextColor = JoyinCharcoal
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp)
        )

        val dbTagsState by viewModel.dbTags.collectAsStateWithLifecycle()
        if (dbTagsState.isNotEmpty()) {
            Text(
                text = t("Database Tags (Tap to toggle)"),
                color = JoyinAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                dbTagsState.forEach { tagObj ->
                    val tagText = tagObj.name
                    val currentList = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    val isSelected = currentList.any { it.equals(tagText, ignoreCase = true) }
                    Surface(
                        color = if (isSelected) JoyinAccent else JoyinLightGrayBG,
                        contentColor = if (isSelected) Color.White else JoyinCharcoal,
                        shape = CircleShape,
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) JoyinAccent else JoyinBorderGray
                        ),
                        modifier = Modifier.clickable {
                            val newList = currentList.toMutableList()
                            if (isSelected) {
                                newList.removeAll { it.equals(tagText, ignoreCase = true) }
                            } else {
                                newList.add(tagText)
                            }
                            tags = newList.joinToString(", ")
                        }
                    ) {
                        Text(
                            text = t(tagText),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.height(6.dp))
        }

        // Red/Warning indicators if user chosen past dates or end before start
        if (isPastSelected) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x3FFF0000)),
                border = BorderStroke(1.dp, Color(0xFFFF3B30)),
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text(
                    text = t("Validation Failed: You cannot schedule an event in the past. (Current UTC time is May 26, 2026)."),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }
        } else if (isEndBeforeStart) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x3FFF0000)),
                border = BorderStroke(1.dp, Color(0xFFFF3B30)),
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text(
                    text = t("Validation Failed: The calendar End Date cannot be physically set before the chosen Start Date."),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Create Event Button
        Button(
            onClick = {
                if (isFormValid) {
                    val finalLocation = if (eventType == "Online") onlineLink else onsiteLocation
                    val finalTags = if (tags.isNotBlank()) tags else "Cairo,Social"
                    val capacityVal = maxCapacityStr.toIntOrNull() ?: 100
                    viewModel.addEvent(
                        title = title,
                        hostName = hostName,
                        location = finalLocation,
                        dateTime = dateTimeSummary,
                        price = priceVal,
                        description = description.ifBlank { "No description provided." },
                        tags = finalTags,
                        audience = audience,
                        dateDay = startDay.toString(),
                        dateWeekday = computedWeekday,
                        imageUrl = selectedImageCover,
                        maxCapacity = capacityVal
                    )
                }
            },
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFormValid) JoyinAccent else JoyinLightGrayBG,
                contentColor = if (isFormValid) JoyinWhite else JoyinLightGray
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("submit_event_btn")
        ) {
            Text(t("Create Event"), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun GoogleSignInGate(
    viewModel: JoyinViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Configure standard Google Sign-In options
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("730568974104-i8tsp26nrdqqhb0klillr8j37cf5vs4a.apps.googleusercontent.com")
            .build()
    }
    
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val displayName = account.displayName ?: "User"
                val email = account.email ?: ""
                val photoUrl = account.photoUrl?.toString()
                
                viewModel.updateLoginState(
                    loggedIn = true,
                    displayName = displayName,
                    email = email,
                    photoUrl = photoUrl
                )
                Toast.makeText(context, "${t("Google Account Verified")}: $displayName", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Google Sign-In Error: ${e.localizedMessage ?: "Connection failure"}", Toast.LENGTH_LONG).show()
        }
    }
    
    // Automatically perform silent sign in check on load
    LaunchedEffect(Unit) {
        val lastAccount = GoogleSignIn.getLastSignedInAccount(context)
        if (lastAccount != null) {
            viewModel.updateLoginState(
                loggedIn = true,
                displayName = lastAccount.displayName ?: "User",
                email = lastAccount.email ?: "",
                photoUrl = lastAccount.photoUrl?.toString()
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Aesthetic illustration icon or avatar representing Auth Join
        Card(
            colors = CardDefaults.cardColors(containerColor = JoyinSelectedRedBg),
            shape = CircleShape,
            modifier = Modifier
                .size(110.dp)
                .padding(bottom = 16.dp),
            border = BorderStroke(2.dp, JoyinAccent)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = FeatherIcons.Unlock,
                    contentDescription = null,
                    tint = JoyinAccent,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = t("Sign in to Joyin"),
            color = JoyinCharcoal,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = t("Please sign in to unlock custom preferences, send secure messages, or create neighborhood events."),
            color = JoyinLightGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 1. Official Branded Google Sign-In Button
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = JoyinWhite,
                contentColor = JoyinCharcoal
            ),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, JoyinBorderGray),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("google_sign_in_btn")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Beautiful Google Brand Logo Draw
                GoogleIconMini()
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = t("Sign in with Google"),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = JoyinCharcoal
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Simulated Sign-In Button (For demo, fast testing on Emulator!)
        Button(
            onClick = {
                viewModel.updateLoginState(
                    loggedIn = true,
                    displayName = "Omar Ramadan",
                    email = "omarrmdn2024@gmail.com",
                    photoUrl = "https://example.com/omar.png"
                )
                Toast.makeText(context, t("You have simulated Google Sign-In successfully!"), Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = JoyinAccent,
                contentColor = JoyinWhite
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("simulation_sign_in_btn")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = FeatherIcons.Activity,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = t("Simulation Sign In"),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun GoogleIconMini() {
    Row(
        modifier = Modifier
            .size(22.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, JoyinBorderGray, CircleShape)
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(7.dp).background(Color(0xFFEA4335), CircleShape))
        Box(modifier = Modifier.size(7.dp).background(Color(0xFF4285F4), CircleShape))
        Box(modifier = Modifier.size(7.dp).background(Color(0xFFFBBC05), CircleShape))
    }
}

@Composable
fun ProfileScreen(viewModel: JoyinViewModel) {
    var showSettings by remember { mutableStateOf(false) }
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val userDisplayName by viewModel.userDisplayName.collectAsStateWithLifecycle()
    val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (showSettings) {
        SettingsScreenLayout(
            viewModel = viewModel,
            onBack = { showSettings = false }
        )
    } else {
        if (!isLoggedIn) {
            GoogleSignInGate(viewModel = viewModel)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .testTag("profile_view_container")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.size(48.dp))

                    Text(
                        text = t("Profile"),
                        color = JoyinCharcoal,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { showSettings = true },
                        modifier = Modifier.minimumInteractiveComponentSize().testTag("profile_settings_icon")
                    ) {
                        Icon(
                            imageVector = FeatherIcons.Settings,
                            contentDescription = "Settings",
                            tint = JoyinCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Profile Card details
                Card(
                    colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Large colored circle avatar using user's initial!
                        val firstChar = if (userDisplayName.isNotEmpty()) userDisplayName.take(1).uppercase() else "J"
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(JoyinAccent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = firstChar,
                                color = JoyinWhite,
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(14.dp))
                        
                        Text(
                            text = t(userDisplayName),
                            color = JoyinCharcoal,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = if (userEmail.isNotEmpty()) userEmail else "verification@joyin.app",
                            color = JoyinLightGray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Google verification badge
                        Surface(
                            color = JoyinSelectedRedBg,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, JoyinAccent.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = FeatherIcons.CheckCircle,
                                    contentDescription = null,
                                    tint = JoyinAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = t("Google Account Verified"),
                                    color = JoyinAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Statistics
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(t("Events Created"), color = JoyinLightGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("1", color = JoyinAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = JoyinLightGrayBG),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(t("Events Attended"), color = JoyinLightGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("3", color = JoyinAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = t("Neighborhood Preferences"),
                    color = JoyinCharcoal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Configuration rows
                ProfileSettingRow(
                    icon = FeatherIcons.Compass, 
                    title = t("Current Neighborhood"), 
                    subtitle = t("Heliopolis, Cairo")
                )
                ProfileSettingRow(
                    icon = FeatherIcons.Bell, 
                    title = t("Alert Notifications"), 
                    subtitle = t("Enabled for weekly running sports")
                )
                ProfileSettingRow(
                    icon = FeatherIcons.Shield, 
                    title = t("Verification Status"), 
                    subtitle = t("Fully Verified Resident")
                )
                
                // App settings row integration
                ProfileSettingRow(
                    icon = FeatherIcons.Settings, 
                    title = t("Settings"), 
                    subtitle = if (isArabicModeActiveByOmar) "خيارات اللغة والمظهر" else "Language and theme options",
                    onClick = { showSettings = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Explicit Google Sign out button
                Button(
                    onClick = {
                        val gsoClean = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                        val clientClean = GoogleSignIn.getClient(context, gsoClean)
                        clientClean.signOut().addOnCompleteListener {
                            viewModel.logout()
                            Toast.makeText(context, t("Sign Out"), Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = JoyinSelectedRedBg,
                        contentColor = JoyinAccent
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_sign_out_btn"),
                    border = BorderStroke(1.dp, JoyinAccent.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = FeatherIcons.LogOut,
                        contentDescription = "Sign Out icon",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = t("Sign Out"), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun SettingsScreenLayout(
    viewModel: JoyinViewModel,
    onBack: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val selectedTheme by viewModel.selectedTheme.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("settings_screen")
    ) {
        // Top Row Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.minimumInteractiveComponentSize().testTag("settings_back_button")
            ) {
                Icon(
                    imageVector = FeatherIcons.ChevronLeft,
                    contentDescription = "Back",
                    tint = JoyinCharcoal,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = t("Settings"),
                color = JoyinCharcoal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Language Settings
        Text(
            text = t("Language"),
            color = JoyinAccent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val langs = listOf(
            "English",
            "Egyptian Arabic"
        )
        
        langs.forEach { lang ->
            val isSelected = selectedLanguage == lang
            val label = if (lang == "English") "English" else "العربية المصرية (مصرى)"
            
            Surface(
                color = if (isSelected) JoyinSelectedRedBg else JoyinLightGrayBG,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) JoyinAccent else JoyinBorderGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { 
                        viewModel.selectLanguage(lang)
                    }
                    .testTag("lang_option_${lang.lowercase().replace(" ", "_")}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) JoyinAccent else JoyinCharcoal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    RadioButton(
                        selected = isSelected,
                        onClick = { viewModel.selectLanguage(lang) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = JoyinAccent,
                            unselectedColor = JoyinLightGray
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Theme Settings
        Text(
            text = t("Theme"),
            color = JoyinAccent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Surface(
            color = JoyinLightGrayBG,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = JoyinBorderGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .testTag("theme_info_premium_dark")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Moon,
                    contentDescription = null,
                    tint = JoyinAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = t("Premium Dark Mode"),
                        color = JoyinCharcoal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = t("Joyin operates exclusively in eye-friendly Premium Dark Mode to save energy and enhance block-level event discovery."),
                        color = JoyinLightGray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ProfileSettingRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(JoyinMediumGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = JoyinAccent,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                color = JoyinCharcoal,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = JoyinLightGray,
                fontSize = 12.sp
            )
        }
    }
}
