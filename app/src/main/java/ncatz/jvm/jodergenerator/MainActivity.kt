package ncatz.jvm.jodergenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activityMain_toolbar)
        supportActionBar?.title = null

        activityMain_actionCopy.setOnClickListener {
            val joder = activityMain_JODER.text.toString()
            when (joder) {
                "- - -" -> Toast.makeText(this, "Genera un JODER™ para copiarlo", Toast.LENGTH_SHORT).show()
                else -> {
                    val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("JODER", joder)
                    clipboard.primaryClip = clip
                    Toast.makeText(this, "JODER™ en el portapapeles", Toast.LENGTH_SHORT).show()
                }
            }
        }
        activityMain_actionShare.setOnClickListener {
            val joder = activityMain_JODER.text.toString()
            when (joder) {
                "- - -" -> Toast.makeText(this, "Genera un JODER™ para compartirlo", Toast.LENGTH_SHORT).show()
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
                min < 5 -> Toast.makeText(this, "Un JODER™ tiene mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                min > 15000 -> Toast.makeText(this, "Un JODER™ tiene máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                max < 5 -> Toast.makeText(this, "Un JODER™ tiene mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                max > 15000 -> Toast.makeText(this, "Un JODER™ tiene máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                min > max -> Toast.makeText(this, "El mínimo es mayor que el máximo", Toast.LENGTH_SHORT).show()
                else -> {
                    activityMain_generate.isEnabled = false
                    activityMain_generate.visibility = View.GONE
                    JODER.generate(min, max, this)
                    activityMain_JODER.scrollTo(0, 0)
                }
            }
        }
        activityMain_generate.setOnLongClickListener {
            activityMain_JODER.text = "- - -"
            activityMain_JODER.scrollTo(0, 0)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        launchSequence()
    }

    private fun launchSequence() {
        val config = ShowcaseConfig()
        config.delay = 100
        config.fadeDuration = 300
        config.contentTextColor = Color.WHITE
        config.dismissTextColor = Color.WHITE
        config.renderOverNavigationBar = true
        config.maskColor = ContextCompat.getColor(this, R.color.showcase)
        val sequenceMain = MaterialShowcaseSequence(this, "id_showcase")
        sequenceMain.setConfig(config)
        sequenceMain.addSequenceItem(activityMain_generate, "Para generar un JODER™ solo tienes que pulsar sobre este señor cabreado", "SIGUIENTE")
        sequenceMain.addSequenceItem(activityMain_generateEdtMin, "El mínimo de caracteres lo puedes indicar aquí (un JODER™ tiene al menos 5 caracteres)", "SIGUIENTE")
        sequenceMain.addSequenceItem(activityMain_generateEdtMax, "Y el máximo, lo eliges aquí (un JODER™ tiene máximo 15.000 caracteres)", "SIGUIENTE")
        sequenceMain.addSequenceItem(activityMain_actionShare, "Con este botón puedes compartir tu exclusivo JODER™ con el resto del mundo", "SIGUIENTE")
        sequenceMain.addSequenceItem(activityMain_actionCopy, "Y con este copiarlo para compartirlo manualmente", "SIGUIENTE")
        sequenceMain.addSequenceItem(activityMain_toolbarInfo, "Por último, aquí arriba podrás ver información sobre la aplicación", "JODER")
        sequenceMain.start()
    }

    companion object {
        fun setJODER(joder: String, mainActivity: MainActivity) {
            mainActivity.runOnUiThread {
                mainActivity.activityMain_JODER.text = joder
                mainActivity.activityMain_generate.visibility = View.VISIBLE
                mainActivity.activityMain_generate.isEnabled = true
            }
        }
    }
}
