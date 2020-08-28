package it.al.blockbreakerworld

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import it.al.blockbreakerworld.game.Game
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var game: Game
    private val _buttons by lazy(LazyThreadSafetyMode.NONE) {buttons}
    private val _gameSurface by lazy(LazyThreadSafetyMode.NONE) {gameSurface}
    private val _gameOverText by lazy(LazyThreadSafetyMode.NONE) {gameOverText}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        Game.levelUrl = intent.getStringExtra("url")!!
        Game.context = this
        Game.addGameOverCallback { onGameOver() }
        Game.addWinCallback { onWin() }
        Game.addErrorCallback { onError() }
        Game.brickSoundPlayer = MediaPlayer.create(Game.context, R.raw.sound_brick)
        Game.gameOverSoundPlayer = MediaPlayer.create(Game.context, R.raw.sound_gameover)
        Game.gameOver = false

        setContentView(R.layout.activity_game)

        retryButton.setOnClickListener(this)
        exitButton.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Game.clear()
    }

    private fun onGameOver() {
        runOnUiThread {
            _gameOverText.text = resources.getString(R.string.txt_game_over)
            _buttons.visibility = View.VISIBLE }
    }

    private fun onWin() {
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
                _buttons.visibility = View.GONE
                _gameSurface.onRetry()
            }

            R.id.exitButton -> finish()
        }
    }
}

