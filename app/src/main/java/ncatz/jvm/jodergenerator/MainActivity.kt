package ncatz.jvm.jodergenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.lid.lib.LabelButtonView
import com.wang.avi.AVLoadingIndicatorView
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

class MainActivity : AppCompatActivity() {

    private lateinit var root: ConstraintLayout
    private lateinit var toolbar: Toolbar
    private lateinit var hamburguer: View

    private lateinit var txtJODER: TextView
    private lateinit var btnGenerate: LabelButtonView
    private lateinit var avLoading: AVLoadingIndicatorView
    private lateinit var btnShare: FloatingActionButton
    private lateinit var btnCopy: FloatingActionButton
    private lateinit var edtMin: EditText
    private lateinit var edtMax: EditText

    private var sequenceMain: MaterialShowcaseSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    override fun onResume() {
        super.onResume()

        if (sequenceMain == null) {
            val config = ShowcaseConfig()
            config.delay = 100
            config.fadeDuration = 300
            config.contentTextColor = Color.WHITE
            config.dismissTextColor = Color.WHITE
            config.renderOverNavigationBar = true
            config.maskColor = ContextCompat.getColor(this, R.color.showcase)
            sequenceMain = MaterialShowcaseSequence(this, "id_showcase")
            sequenceMain!!.setConfig(config)
            sequenceMain!!.addSequenceItem(btnGenerate, "Para generar un JODER™ solo tienes que pulsar sobre este señor cabreado", "SIGUIENTE")
            sequenceMain!!.addSequenceItem(edtMin, "El mínimo de caracteres lo puedes indicar aquí (un JODER™ tiene al menos 5 caracteres)", "SIGUIENTE")
            sequenceMain!!.addSequenceItem(edtMax, "Y el máximo, lo eliges aquí (un JODER™ tiene máximo 15.000 caracteres)", "SIGUIENTE")
            sequenceMain!!.addSequenceItem(btnShare, "Con este botón puedes compartir tu exclusivo JODER™ con el resto del mundo", "SIGUIENTE")
            sequenceMain!!.addSequenceItem(btnCopy, "Y con este copiarlo para compartirlo manualmente", "SIGUIENTE")
            sequenceMain!!.addSequenceItem(hamburguer, "Por último, aquí arriba podrás ver información sobre la aplicación", "JODER")
            sequenceMain!!.start()
        }
    }

    private fun initViews() {
        root = findViewById(R.id.activityMain_parentLayout)
        toolbar = findViewById(R.id.activityMain_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = null

        hamburguer = findViewById(R.id.activityMain_toolbarInfo)
        txtJODER = findViewById(R.id.activityMain_JODER)
        txtJODER.movementMethod = ScrollingMovementMethod()
        avLoading = findViewById(R.id.activityMain_avLoading)
        btnGenerate = findViewById(R.id.activityMain_generate)
        btnShare = findViewById(R.id.activityMain_actionShare)
        btnCopy = findViewById(R.id.activityMain_actionCopy)
        edtMin = findViewById(R.id.activityMain_generateEdtMin)
        edtMax = findViewById(R.id.activityMain_generateEdtMax)

        btnCopy.setOnClickListener {
            val joder = txtJODER.text.toString()
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
        btnShare.setOnClickListener {
            val joder = txtJODER.text.toString()
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

        btnGenerate.setOnClickListener {
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            var min = 0
            var max = 0
            if (edtMin.text.toString() != "") {
                min = Integer.parseInt(edtMin.text.toString())
            }
            if (edtMax.text.toString() != "") {
                max = Integer.parseInt(edtMax.text.toString())
            }
            when {
                min < 5 -> Toast.makeText(this, "Un JODER™ tiene mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                min > 15000 -> Toast.makeText(this, "Un JODER™ tiene máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                max < 5 -> Toast.makeText(this, "Un JODER™ tiene mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                max > 15000 -> Toast.makeText(this, "Un JODER™ tiene máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                min > max -> Toast.makeText(this, "El mínimo es mayor que el máximo", Toast.LENGTH_SHORT).show()
                else -> {
                    btnGenerate.isEnabled = false
                    btnGenerate.visibility = View.GONE
                    JODER.generate(min, max, this)
                    txtJODER.scrollTo(0, 0)
                }
            }
        }
        btnGenerate.setOnLongClickListener {
            txtJODER.text = "- - -"
            txtJODER.scrollTo(0, 0)
            true
        }
    }

    companion object {
        fun setJODER(joder: String, mainActivity: MainActivity) {
            mainActivity.runOnUiThread {
                mainActivity.txtJODER.text = joder
                mainActivity.btnGenerate.visibility = View.VISIBLE
                mainActivity.btnGenerate.isEnabled = true
            }
        }
    }
}
