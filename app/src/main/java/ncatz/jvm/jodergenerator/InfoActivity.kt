package ncatz.jvm.jodergenerator

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class InfoActivity : AppCompatActivity() {

    private lateinit var twitter_kast: LinearLayout
    private lateinit var git_kast: LinearLayout
    private lateinit var twitter_alex: LinearLayout
    private lateinit var git_alex: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        twitter_kast = findViewById(R.id.twk)
        git_kast = findViewById(R.id.gitk)
        twitter_alex = findViewById(R.id.twa)
        git_alex = findViewById(R.id.gita)
        twitter_kast.setOnClickListener { open("https://twitter.com/KastJMD") }
        git_kast.setOnClickListener { open("https://github.com/JMedinilla/JODERKotlin") }
        twitter_alex.setOnClickListener { open("https://twitter.com/AlexPowerUp") }
        git_alex.setOnClickListener { open("https://github.com/alexpowerup/JODERGenerator") }
    }

    private fun open(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
