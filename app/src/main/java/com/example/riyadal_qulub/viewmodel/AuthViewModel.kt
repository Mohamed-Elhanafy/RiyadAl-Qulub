import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _signInError = MutableLiveData<String?>()
    val signInError: LiveData<String?> = _signInError

    private val _signUpError = MutableLiveData<String?>()
    val signUpError: LiveData<String?> = _signUpError

    private val auth = FirebaseAuth.getInstance()

    init {
        // Check if the user is already authenticated
        _currentUser.value = auth.currentUser
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                    _signInError.value = null
                } else {
                    _currentUser.value = null
                    _signInError.value = task.exception?.message
                }
            }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                    _signUpError.value = null
                } else {
                    _currentUser.value = null
                    _signUpError.value = task.exception?.message
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }
}