import java.text.DecimalFormat
import kotlin.system.exitProcess

const val HUNDRED: Int = 100
const val LIMIT_DAY_MAX: Int = 150_000_00
const val LIMIT_MONTH_MAX: Int = 600_000_00
const val LIMIT_MASTERCARD_MAX: Int = 75000_00
const val PROCENT_MASTERCARD: Double = 0.6
const val FIX_SUM_MASTERCARD: Int = 20_00
const val LIMIT_VISA_MIN: Int = 35_00
const val LIMIT_VISA_MAX: Int = 150_000_00
const val LIMIT_VK_MIN: Int = 15000_00
const val LIMIT_VK_MAX: Int = 40000_00

fun main() {

    println("Введите сумму перевода:")
    println("Введите рубли:")
    val rub = readln()
    println("Введите копейки или Enter (по умолчанию 0 копеек):")
    var kop = readln()
    if (kop == "") kop = "0"
    println("Введите тип карты: (1, 2, 3, 4, 5) или нажмите Enter (по умолчанию VK Pay)")
    println("1. MasterCard")
    println("2. Maestro")
    println("3. Visa")
    println("4. МИР")
    println("5. VK Pay")
    var typeRead = readln()
    if (typeRead == "") typeRead = "5"
    println("Введите сумму предыдущих покупок за месяц в рублях или нажмите Enter, если покупок не было: (по умолчанию 0)")
    var sumMonthRead = readln()
    if (sumMonthRead == "") sumMonthRead = "0"
    val amountRu: Int
    val amountK: Int
    val amount: Int
    val sumMonth: Int
    val type: Int

    try {
        amountRu = rub.toInt()
        amountK = kop.toInt()
        type = typeRead.toInt()
        val sumMonthK = sumMonthRead.toInt()
        sumMonth = sumMonthK * HUNDRED
        amount = amountRu * HUNDRED + amountK
        print("Сумма желаемого перевода равна:")
        println("  $amountRu руб. $amountK коп.")

    } catch (e: Exception) {
        println("Вы ввели не число")
        exitProcess(-1)
    }
    println("Сумма покупок за предыдущий месяц: ${sumMonth / HUNDRED} руб.")
    println("______________________________________________________________")
    if ((amount > LIMIT_DAY_MAX) || (sumMonth > LIMIT_MONTH_MAX)) {
        println("Превышен Ваш лимит 150000 рублей в сутки или 600000 рублей в месяц")
    } else commission(type, sumMonth, amount)
    println("Сумма комиссии за перевод: ${DecimalFormat("#.##").format(commission(type, sumMonth, amount) / HUNDRED)}")
}

fun commission(
    type: Int = 5,
    sumMonth: Int = 0,
    amount: Int
): Double {
    return when (type) {
        1, 2 -> {
            mastercard(amount)
        }
        3, 4 -> {
            visa(amount)
        }
        5 -> {
            vk(amount, sumMonth)
        }
        else -> {
            println("Ошибка перевода!")
            0.0
        }
    }
}

private fun mastercard(amount: Int) = if (amount < LIMIT_MASTERCARD_MAX) {
    val perevod = (amount / HUNDRED.toDouble())
    val commission1 = 0.0
    sumPrint(perevod)
    commission1
} else {
    val commission = ((amount * (PROCENT_MASTERCARD / HUNDRED)) + FIX_SUM_MASTERCARD)
    val perevod = (amount + (commission)) / HUNDRED
    val commission1 = commission / HUNDRED
    sumPrint(perevod)
    commission
}

private fun visa(amount: Int) = if (amount < LIMIT_VISA_MAX) {
    var commission = (amount * 0.75 / HUNDRED)
    println(commission / HUNDRED)
    if (commission < LIMIT_VISA_MIN) {
        commission = LIMIT_VISA_MIN.toDouble()
    }
    val perevod = (amount + (commission)) / HUNDRED
    val commission1 = commission / HUNDRED
    sumPrint(perevod)
    commission

} else {
    println("Введенная сумма более 150000 руб. перевод невозможен")
    0.0
}

private fun vk(amount: Int, sumMonth: Int) = if ((amount > LIMIT_VK_MIN) || (LIMIT_VK_MAX < sumMonth)) {
    println("Превышен лимит для VK Pay. Максимальная сумма 15000 руб. за один раз и не боллее 40000 руб. в месяц.")
    0.0
} else {
    val perevod = (amount / HUNDRED.toDouble())
    val commission1 = 0.0
    sumPrint(perevod)
    commission1
}

private fun sumPrint(perevod: Double) {
    println("Всего списано за перевод: ${DecimalFormat("#.##").format(perevod)}")

}



