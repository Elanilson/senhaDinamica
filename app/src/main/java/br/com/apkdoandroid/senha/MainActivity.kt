package br.com.apkdoandroid.senha

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.apkdoandroid.senha.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private var senhaDigitada: String = ""
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick(){
        binding.buttonResetar.setOnClickListener {
            limparDigitos()
        }
        binding.buttonConfirmar.setOnClickListener {
            verificarSenha()
        }
        digitos()
    }
    private fun digitos(){

        val buttons = listOf<Button>(
            binding.buttonNumero0,
            binding.buttonNumero1,
            binding.buttonNumero2,
            binding.buttonNumero3,
            binding.buttonNumero4,
            binding.buttonNumero5,
            binding.buttonNumero6,
            binding.buttonNumero7,
            binding.buttonNumero8,
            binding.buttonNumero9
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                if (senhaDigitada.length < 4) {
                    val numeroDigitado = button.text.toString()
                    senhaDigitada += numeroDigitado
                    binding.textViewDisplay.text = senhaDigitada
                }
            }
        }
    }


    fun mostrarSenhaCorretaDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Senha Correta")
        builder.setMessage("Você acertou a senha!")
        builder.setPositiveButton("OK") { dialog, _ ->
            limparDigitos()
            dialog.dismiss()
        }
        builder.create().show()
    }

    // Método para exibir um diálogo indicando que a senha está incorreta
    fun mostrarSenhaIncorretaDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Senha Incorreta")
        builder.setMessage("Você errou a senha.")
        builder.setPositiveButton("OK") { dialog, _ ->
            limparDigitos()
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun limparDigitos() {
        senhaDigitada = ""
        binding.textViewDisplay.text = senhaDigitada
    }

    private fun calcularSenha(): String {
        val calendar = Calendar.getInstance()
        val diaAtual = calendar.get(Calendar.DAY_OF_MONTH)
        val mesAtual = calendar.get(Calendar.MONTH) + 1 // Adicione 1 porque os meses em Calendar são baseados em 0
        val part1 = 20 + diaAtual
        val part2 = 11 + mesAtual
        val senha = part1.toString() + part2.toString()
        Log.d("senhaCalculada","senha: ${senha}  dia - ${part1} mes- ${part2}")
        return senha
    }

    private fun verificarSenha() {
        val senhaCorreta = calcularSenha()

        if (senhaDigitada == senhaCorreta) {
            // Senha correta
            mostrarSenhaCorretaDialog()
        } else {
            // Senha incorreta
            mostrarSenhaIncorretaDialog()
        }
    }
}