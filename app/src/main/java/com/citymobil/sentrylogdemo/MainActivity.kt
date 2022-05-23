package com.citymobil.sentrylogdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.citymobil.sentrylogdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import io.sentry.Sentry
import io.sentry.SentryEvent
import io.sentry.protocol.Message


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val VER = 23
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar
                .make(view, "Exception throwed!$VER", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

            fun1UndastandableName()
        }
    }

    private fun fun1UndastandableName() {
        fun2AlsoUndastandableName()
    }

    private fun fun2AlsoUndastandableName() {
        try {
            throw AssertionError("This is a test.$VER")
        } catch (e: Throwable) {
            val event = SentryEvent(e)
            event.message = Message().apply {
                this.message = "Deserialization error: requestInfo.url, ${e.message}"
            }
            event.setTag("url", "requestInfo.url")

            Sentry.captureEvent(event)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}