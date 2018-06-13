package ncatz.jvm.jodergenerator

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    companion object {
        const val JODER_MAX: Int = 150000
    }

    private val joder = JODER()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activityMain_toolbar)
        supportActionBar?.title = null
        activityMain_JODER.movementMethod = ScrollingMovementMethod()

        activityMain_actionCopy.setOnClickListener {
            val joder = activityMain_JODER.text.toString()
            when (joder) {
                "- - -" -> Toast.makeText(this, getString(R.string.joderToCopy), Toast.LENGTH_SHORT).show()
                else -> {
                    val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText(getString(R.string.joder), joder)
                    clipboard.primaryClip = clip
                    Toast.makeText(this, getString(R.string.joderCopied), Toast.LENGTH_SHORT).show()
                }
            }
        }
        activityMain_actionShare.setOnClickListener {
            val joder = activityMain_JODER.text.toString()
            when (joder) {
                "- - -" -> Toast.makeText(this, getString(R.string.joderToShare), Toast.LENGTH_SHORT).show()
                else -> {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(Intent.EXTRA_TEXT, joder)
                    shareIntent.type = "text/plain"
                    startActivity(shareIntent)
                }
            }
        }
        activityMain_generate.setOnClickListener {
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            var min = 0
            var max = 0
            if (activityMain_generateEdtMin.text.toString() != "") {
                min = Integer.parseInt(activityMain_generateEdtMin.text.toString())
            }
            if (activityMain_generateEdtMax.text.toString() != "") {
                max = Integer.parseInt(activityMain_generateEdtMax.text.toString())
            }
            when {
                min < 5 -> Toast.makeText(this, getString(R.string.minFiveChar), Toast.LENGTH_SHORT).show()
                min > JODER_MAX -> Toast.makeText(this, getString(R.string.minHighChar), Toast.LENGTH_SHORT).show()
                max < 5 -> Toast.makeText(this, getString(R.string.maxFiveChar), Toast.LENGTH_SHORT).show()
                max > JODER_MAX -> Toast.makeText(this, getString(R.string.maxHighChar), Toast.LENGTH_SHORT).show()
                min > max -> Toast.makeText(this, getString(R.string.minAndMaxChar), Toast.LENGTH_SHORT).show()
                else -> {
                    activityMain_JODER.text = "- - -"
                    activityMain_JODER.scrollTo(0, 0)
                    activityMain_generate.isEnabled = false
                    activityMain_generate.visibility = View.GONE
                    joder.generate(min, max)
                    activityMain_JODER.scrollTo(0, 0)
                }
            }
        }
        activityMain_generate.setOnLongClickListener {
            activityMain_JODER.text = "- - -"
            activityMain_JODER.scrollTo(0, 0)
            true
        }
        activityMain_generateEdtMin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val number = s.toString().toInt()
                if (number > JODER_MAX) {
                    activityMain_generateEdtMin.setText(JODER_MAX.toString())
                }
            }
        })
        activityMain_generateEdtMax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val number = s.toString().toInt()
                if (number > JODER_MAX) {
                    activityMain_generateEdtMax.setText(JODER_MAX.toString())
                }
            }

        })
        activityMain_toolbarInfo.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_info)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val githubJM = dialog.findViewById<TextView>(R.id.activityInfo_gitJM)
            val githubAPU = dialog.findViewById<TextView>(R.id.activityInfo_gitAPU)
            val twitterAPU = dialog.findViewById<TextView>(R.id.activityInfo_twAPU)
            val intent = Intent(Intent.ACTION_VIEW)
            githubJM.setOnClickListener {
                val url = "https://github.com/JMedinilla"
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            githubAPU.setOnClickListener {
                val url = "https://github.com/alexpowerup"
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            twitterAPU.setOnClickListener {
                val url = "https://twitter.com/alexpowerup"
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            dialog.show()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onJoderEvent(event: JODER.JoderEvent) {
        activityMain_JODER.text = event.joderGenerated
        activityMain_generate.visibility = View.VISIBLE
        activityMain_generate.isEnabled = true
    }
}
