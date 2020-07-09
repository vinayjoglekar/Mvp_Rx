package com.jovinz.mvp_rx.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jovinz.mvp_rx.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var disposable: DisposableObserver<Boolean?>
    private var emailChangeObservable: Observable<CharSequence>? = null
    private var passwordChangeObservable: Observable<CharSequence>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailChangeObservable =
            RxTextView.textChanges(demo_combl_email).skip(1)

        passwordChangeObservable =
            RxTextView.textChanges(demo_combl_password).skip(1)

        val observable: Observable<Boolean> =
            Observable.combineLatest(
                emailChangeObservable,
                passwordChangeObservable,
                BiFunction { email, pwd ->
                    isValidForm(email.toString(), pwd.toString())
                })

        disposable = object : DisposableObserver<Boolean?>() {
            override fun onComplete() {}

            override fun onNext(isEnabled: Boolean) {
                btn_demo_form_valid.isEnabled = isEnabled
            }

            override fun onError(e: Throwable) {}

        }
        observable.subscribe(disposable)

        btn_demo_form_valid.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun isValidForm(email: String, password: String): Boolean {
        val validEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!validEmail) {
            demo_combl_email.error = getString(R.string.email_validation)
        }
        val validPass = password.isNotEmpty() && password.length <= 8
        if (!validPass) {
            demo_combl_password.error = getString(R.string.pass_validation)
        }
        return validEmail && validPass
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}