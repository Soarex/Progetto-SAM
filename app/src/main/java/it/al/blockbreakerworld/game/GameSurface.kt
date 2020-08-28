package it.al.blockbreakerworld.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameSurface : SurfaceView,
    SurfaceHolder.Callback {
    private lateinit var _gameThread: GameThread
    var layer = MainLayer()

    val gameThread
        get() = _gameThread

    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle)

    fun onInit() {
        layer.onInit()
    }

    fun onUpdate(deltaTime: Float) {
        layer.onUpdate(deltaTime)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        layer.onDraw(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        ScreenMetrics.height = resources.displayMetrics.heightPixels
        ScreenMetrics.width = resources.displayMetrics.widthPixels
        ScreenMetrics.density = resources.displayMetrics.density
        ScreenMetrics.dpi =  resources.displayMetrics.densityDpi

        _gameThread = GameThread(this, holder)
        _gameThread.setRunning(true)
        _gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        ScreenMetrics.width = width
        ScreenMetrics.height = height
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                _gameThread.setRunning(false)
                _gameThread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    init {
        this.isFocusable = true
        this.holder.addCallback(this)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
            Input.touchPosition.x =
                pxToDp(event.x)
            Input.touchPosition.y =
                pxToDp(event.y)

        return true
    }

    fun onRetry() {
        Game.gameOver = false
        layer = MainLayer()
        layer.onInit()
    }
}