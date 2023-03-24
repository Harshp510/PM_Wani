
package com.zenwsmp.pmwani.wifi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.zenwsmp.pmwani.wifi.annotation.OpenClass
import com.zenwsmp.pmwani.wifi.util.EMPTY
import com.zenwsmp.pmwani.wifi.util.createContext
import com.zenwsmp.pmwani.wifi.permission.PermissionService
import com.zenwsmp.pmwani.wifi.settings.Repository
import com.zenwsmp.pmwani.wifi.settings.Settings
import com.zenwsmp.pmwani.wifi.scanner.ScannerService
import com.zenwsmp.pmwani.R
import com.zenwsmp.pmwani.wifi.accesspoint.ConnectionView


@OpenClass
class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
   // internal lateinit var drawerNavigation: DrawerNavigation
    internal lateinit var mainReload: MainReload

    internal lateinit var permissionService: PermissionService
    internal lateinit var connectionView: ConnectionView

    private var currentCountryCode: String = String.EMPTY


    override fun attachBaseContext(newBase: Context) =
        super.attachBaseContext(newBase.createContext(Settings(Repository(newBase)).languageLocale()))

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainContext = MainContext.INSTANCE
        //mainContext.initialize(this, largeScreen)

        val settings = mainContext.settings
        settings.initializeDefaultValues()
        setTheme(settings.themeStyle().themeNoActionBar)
        setWiFiChannelPairs(mainContext)

        mainReload = MainReload(settings)

        super.onCreate(savedInstanceState)
      //  installSplashScreen()
        setContentView(R.layout.main_activity)

        settings.registerOnSharedPreferenceChangeListener(this)
      //  optionMenu = OptionMenu()

        keepScreenOn()

    /*    val toolbar = setupToolbar()
        drawerNavigation = DrawerNavigation(this, toolbar)
        drawerNavigation.create()

        navigationMenuController = NavigationMenuController(this)
        navigationMenuController.currentNavigationMenu(settings.selectedMenu())
        onNavigationItemSelected(currentMenuItem())*/

       connectionView = ConnectionView(this)
        permissionService = PermissionService(this)

        //onBackPressedDispatcher.addCallback(this, MainActivityBackPressed(this))


    }



   /* override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (!permissionService.granted(requestCode, grantResults)) {
            finish()
        }else{
            val intent = Intent(this, AccessPointsActivity::class.java)
            startActivity(intent)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/

    private fun setWiFiChannelPairs(mainContext: MainContext) {
        val settings = mainContext.settings
        val countryCode = settings.countryCode()
        if (countryCode != currentCountryCode) {
            mainContext.configuration.wiFiChannelPair(countryCode)
            currentCountryCode = countryCode
        }
    }

    private val largeScreen: Boolean
        get() {
            val configuration = resources.configuration
            val screenLayoutSize = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
            return screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                    screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_XLARGE
        }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val mainContext = MainContext.INSTANCE
        if (mainReload.shouldReload(mainContext.settings)) {
            MainContext.INSTANCE.scannerService.stop()
            recreate()
        } else {
            keepScreenOn()
            setWiFiChannelPairs(mainContext)
            update()
        }
    }

    fun update() {
        MainContext.INSTANCE.scannerService.update()
       // updateActionBar()
    }





    public override fun onPause() {
        val scannerService: ScannerService = MainContext.INSTANCE.scannerService
        scannerService.pause()
        scannerService.unregister(connectionView)
       // updateActionBar()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        /*val scannerService: ScannerService = MainContext.INSTANCE.scannerService
        if (permissionService.permissionGranted()) {
            if (!permissionService.systemEnabled()) {
                startLocationSettings()
            }

        } else {
            scannerService.pause()
        }*/
        val scannerService: ScannerService = MainContext.INSTANCE.scannerService
        scannerService.resume()
       // updateActionBar()
        scannerService.register(connectionView)
    }

    public override fun onStop() {
        MainContext.INSTANCE.scannerService.stop()
        super.onStop()
    }

   /* public override fun onStart() {
        super.onStart()
        if (permissionService.permissionGranted()) {
            MainContext.INSTANCE.scannerService.resume()
        } else {
            permissionService.check()
        }
    }*/



}