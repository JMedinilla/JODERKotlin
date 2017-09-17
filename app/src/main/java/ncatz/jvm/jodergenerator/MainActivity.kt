package ncatz.jvm.jodergenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.getbase.floatingactionbutton.FloatingActionButton
import com.lid.lib.LabelButtonView
import com.wang.avi.AVLoadingIndicatorView
import com.yalantis.guillotine.animation.GuillotineAnimation
import com.yalantis.guillotine.interfaces.GuillotineListener

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
    private lateinit var guillotineGitK: ImageView
    private lateinit var guillotineGitA: ImageView

    private var guillotineOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
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
            val min = Integer.parseInt(edtMin.text.toString())
            val max = Integer.parseInt(edtMax.text.toString())
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
            txtJODER.text = "- - -"
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
                    }

                    override fun onGuillotineOpened() {
                        guillotineOpen = true
                    }
                })
                .setClosedOnStart(true)
                .build()

        guillotineTwA = guillotine.findViewById(R.id.twa)
        guillotineGitK = guillotine.findViewById(R.id.imgJM)
        guillotineGitA = guillotine.findViewById(R.id.imgAPU)
        guillotineGitK.setOnClickListener { open("https://github.com/JMedinilla/JODERKotlin") }
        guillotineTwA.setOnClickListener { open("https://twitter.com/AlexPowerUp") }
        guillotineGitA.setOnClickListener { open("https://github.com/alexpowerup/JODERGenerator") }
    }

    private fun open(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onBackPressed() {
        when {
            guillotineOpen -> guillotineAnimation.close()
            else -> super.onBackPressed()
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
