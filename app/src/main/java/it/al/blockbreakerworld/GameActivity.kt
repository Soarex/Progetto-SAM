package it.al.blockbreakerworld

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import it.al.blockbreakerworld.engine.*
import it.al.blockbreakerworld.game.Game
import it.al.blockbreakerworld.game.MainScene
import kotlinx.android.synthetic.main.activity_game.*
import java.text.DecimalFormat

class GameActivity : AppCompatActivity(), View.OnClickListener, SensorEventListener {
    private val _buttons by lazy(LazyThreadSafetyMode.NONE) {buttons}
    private val _gameOverText by lazy(LazyThreadSafetyMode.NONE) {gameOverText}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        Game.clear()
        Game.levelUrl = intent.getStringExtra("url")!!
        Game.addGameOverCallback { onGameOver() }
        Game.addWinCallback { onWin() }
        Game.addErrorCallback { onError() }

        Game.brickSoundPlayer = MediaPlayer.create(this, R.raw.sound_brick)
        Game.gameOverSoundPlayer = MediaPlayer.create(this, R.raw.sound_gameover)

        EngineEvents.addOnInitListener { SceneManager.loadNewScene(MainScene()) }

        setContentView(R.layout.activity_game)

        retryButton.setOnClickListener(this)
        exitButton.setOnClickListener(this)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onDestroy() {
        super.onDestroy()
        Game.release()
    }

    private fun onGameOver() {
        Game.gameOverSoundPlayer.seekTo(0)
        Game.gameOverSoundPlayer.start()

        EntityManager.clear()

        runOnUiThread {
            _gameOverText.text = resources.getString(R.string.txt_game_over)
            _buttons.visibility = View.VISIBLE }
    }

    private fun onWin() {
        EntityManager.clear()

        runOnUiThread {
            _gameOverText.text = resources.getString(R.string.txt_win)
            _buttons.visibility = View.VISIBLE }
    }

    private fun onError() {
        runOnUiThread {
            _gameOverText.text = resources.getString(R.string.txt_error)
            _buttons.visibility = View.VISIBLE }
    }

    override fun onClick(p0: View?) {
        if(p0 == null) return

        when(p0.id) {
            R.id.retryButton -> {
                finish()
                startActivity(intent)
            }

            R.id.exitButton -> finish()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val axisX: Float = event.values[0]
            val axisY: Float = event.values[1]
            var axisZ: Float = event.values[2]

            val df = DecimalFormat("#.#")
            Input.gyroInput.x = df.format(axisX).toFloat()
            Input.gyroInput.y = df.format(axisY).toFloat()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}

