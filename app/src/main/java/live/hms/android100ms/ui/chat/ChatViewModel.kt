package live.hms.android100ms.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    private val _messages = ArrayList<ChatMessage>()
    private var sendBroadcastCallback: ((ChatMessage) -> Unit)? = null

    fun setSendBroadcastCallback(callback: ((message: ChatMessage) -> Unit)) {
        sendBroadcastCallback = callback
    }

    fun removeSendBroadcastCallback() {
        sendBroadcastCallback = null
    }

    private val messages = MutableLiveData<ArrayList<ChatMessage>>()
    fun getMessages(): LiveData<ArrayList<ChatMessage>> = messages
    fun clearMessages() {
        _messages.clear()
        messages.value = _messages
    }

    private fun addMessage(message: ChatMessage) {
        _messages.add(message)
        messages.value = _messages
    }

    fun broadcast(message: ChatMessage) {
        Log.v(TAG, "broadcastMessage: $message")
        sendBroadcastCallback?.let { it(message) }
        addMessage(message)
    }

    fun receivedMessage(message: ChatMessage) {
        Log.v(TAG, "receivedMessage: $message")
        addMessage(message)
    }
}