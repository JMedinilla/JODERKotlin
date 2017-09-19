package ncatz.jvm.jodergenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import com.lid.lib.LabelButtonView
import com.wang.avi.AVLoadingIndicatorView
import com.yalantis.guillotine.animation.GuillotineAnimation
import com.yalantis.guillotine.interfaces.GuillotineListener
import kotlinx.android.synthetic.main.guillotine.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

class MainActivity : AppCompatActivity() {

    private lateinit var root: FrameLayout
    private lateinit var toolbar: Toolbar
    private lateinit var hamburguer: View

    private lateinit var txtJODER: CanaroText
    private lateinit var btnGenerate: LabelButtonView
    private lateinit var avLoading: AVLoadingIndicatorView
    private lateinit var btnShare: FloatingActionButton
    private lateinit var btnCopy: FloatingActionButton
    private lateinit var edtMin: EditText
    private lateinit var edtMax: EditText

    private lateinit var guillotine: View
    private lateinit var guillotineAnimation: GuillotineAnimation
    private lateinit var guillotineTwA: LinearLayout
    private lateinit var guillotineGitKImg: ImageView
    private lateinit var guillotineGitK: LinearLayout
    private lateinit var guillotineGitA: LinearLayout

    private var sequenceMain: MaterialShowcaseSequence? = null
    private var sequenceGuillotine: MaterialShowcaseSequence? = null

    private var guillotineOpen: Boolean = false

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

    @SuppressLint("InflateParams")
    private fun initViews() {
        root = findViewById(R.id.root)
        toolbar = findViewById(R.id.toolbar)
        hamburguer = findViewById(R.id.hamburguer)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = null

        txtJODER = findViewById(R.id.txtJODER)
        txtJODER.movementMethod = ScrollingMovementMethod()
        avLoading = findViewById(R.id.avLoading)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnShare = findViewById(R.id.actionShare)
        btnCopy = findViewById(R.id.actionCopy)
        edtMin = findViewById(R.id.edtMin)
        edtMax = findViewById(R.id.edtMax)

        btnCopy.setOnClickListener {
            if (guillotineOpen)
                return@setOnClickListener

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
            if (guillotineOpen)
                return@setOnClickListener

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
            if (guillotineOpen)
                return@setOnClickListener

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
                min < 5 -> Toast.makeText(this, "Un JODER™ tine mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                min > 15000 -> Toast.makeText(this, "Un JODER™ tine máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                max < 5 -> Toast.makeText(this, "Un JODER™ tine mínimo 5 caracteres", Toast.LENGTH_SHORT).show()
                max > 15000 -> Toast.makeText(this, "Un JODER™ tine máximo 15.000 caracteres", Toast.LENGTH_SHORT).show()
                min > max -> Toast.makeText(this, "El mínimo es mayor que el máximo", Toast.LENGTH_SHORT).show()
                else -> {
                    btnGenerate.isEnabled = false
                    btnGenerate.visibility = View.GONE
                    JODER.generate(min, max, this)
                }
            }
        }
        btnGenerate.setOnLongClickListener {
            if (!guillotineOpen) {
                txtJODER.text = "- - -"
            }
            true
        }

        guillotine = LayoutInflater.from(this).inflate(R.layout.guillotine, null)
        root.addView(guillotine)
        guillotineAnimation = GuillotineAnimation.GuillotineBuilder(guillotine, guillotine.findViewById(R.id.toolbarLayout), hamburguer)
                .setStartDelay(250)
                .setActionBarViewForAnimation(toolbar)
                .setGuillotineListener(object : GuillotineListener {
                    override fun onGuillotineClosed() {
                        guillotineOpen = false

                        edtMax.isEnabled = true
                        edtMin.isEnabled = true
                    }

                    override fun onGuillotineOpened() {
                        guillotineOpen = true

                        edtMax.isEnabled = false
                        edtMin.isEnabled = false

                        val config = ShowcaseConfig()
                        config.delay = 100
                        config.fadeDuration = 300
                        config.contentTextColor = Color.WHITE
                        config.dismissTextColor = Color.WHITE
                        config.renderOverNavigationBar = true
                        config.maskColor = ContextCompat.getColor(applicationContext, R.color.showcase)
                        sequenceGuillotine = MaterialShowcaseSequence(this@MainActivity, "id_guillotine")
                        sequenceGuillotine!!.setConfig(config)
                        sequenceGuillotine!!.addSequenceItem(imgAPU, "Este subnormal es el creador original de JODERGenerator para Windows, debajo tienes su Twitter y el código fuente (C#) con el ejecutable para Windows", "SIGUIENTE")
                        sequenceGuillotine!!.addSequenceItem(canaroGitK, "Si te interesa ver cómo está hecha esta aplicación (en Kotlin), puedes verlo aquí", "JODER")
                        sequenceGuillotine!!.start()
                    }
                })
                .setClosedOnStart(true)
                .build()

        guillotineTwA = guillotine.findViewById(R.id.twa)
        guillotineGitKImg = guillotine.findViewById(R.id.imgJM)
        guillotineGitK = guillotine.findViewById(R.id.gitk)
        guillotineGitA = guillotine.findViewById(R.id.gita)

        guillotineGitKImg.setOnClickListener { open("https://github.com/JMedinilla/JODERKotlin") }
        guillotineGitK.setOnClickListener { open("https://github.com/JMedinilla/JODERKotlin") }
        guillotineTwA.setOnClickListener { open("https://twitter.com/AlexPowerUp") }
        guillotineGitA.setOnClickListener { open("https://github.com/alexpowerup/JODERGenerator") }
    }

    private fun open(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onBackPressed() {
        if (sequenceGuillotine != null && sequenceGuillotine!!.hasFired()) {
            when {
                guillotineOpen -> guillotineAnimation.close()
                else -> super.onBackPressed()
            }
        } else if (sequenceGuillotine == null) {
            when {
                guillotineOpen -> guillotineAnimation.close()
                else -> super.onBackPressed()
            }
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
