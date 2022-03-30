import kotlin.math.roundToInt
import kotlin.system.exitProcess

fun main() {

    println("Введите сумму перевода:")
    println("Введите рубли:")
    val rub = readln()
    println("Введите копейки:")
    val kop = readln()
    println("Введите тип карты: (1, 2, 3, 4, 5)")
    println("1. MasterCard")
    println("2. Maestro")
    println("3. Visa")
    println("4. МИР")
    println("5. VK Pay")
    val typeRead = readln()
    println("Введите сумму предыдущих покупок за месяц в рублях или нажмите Enter, если покупок не было:")
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
        sumMonth = sumMonthK * 100
        amount = amountRu * 100 + amountK
        print("Сумма желаемого перевода равна:")
        println("  $amountRu руб. $amountK коп.")

    } catch (e: Exception) {
        println("Вы ввели не число")
        exitProcess(-1)
    }
    println("Сумма покупок за предыдущий месяц: ${sumMonth / 100} руб.")
    println("______________________________________________________________")
    if ((amount > 150_000_00) || (sumMonth > 600_000_00)) {
        println("Превышен Ваш лимит 150000 рублей в сутки или 600000 рублей в месяц")
    } else commission(type, sumMonth, amount)
}

fun commission(
    type: Int,
    sumMonth: Int,
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

private fun mastercard(amount: Int) = if (amount < 75000_00) {
    val perevod = (amount / 100.00)
    val procent1 = 0.0
    sumPrint(perevod, procent1)
    procent1
} else {
    val procent = ((amount * (0.6 / 100)) + 20_00)
    val perevod = (amount + (procent)) / 100
    val procent1 = procent / 100
    sumPrint(perevod, procent1)
    procent
}

private fun visa(amount: Int) = if ((35_00 < amount) && (amount < 150_000_00)) {
    var procent = (amount * 0.75 / 100)
    if (procent < 35_00) {
        procent = 35_00.0
    }
    val perevod = (amount + (procent)) / 100
    val procent1 = procent / 100
    sumPrint(perevod, procent1)
    procent

} else {
    println("Введенная сумма меньше 35 руб или более 150000 руб. перевод невозможен")
    0.0
}

private fun vk(amount: Int, sumMonth: Int) = if ((amount > 15000_00) || (40000_00 < sumMonth)) {
    println("Превышен лимит для VK Pay. Максимальная сумма 15000 руб. за один раз и не боллее 40000 руб. в месяц.")
    0.0
} else {
    val perevod = (amount / 100.00)
    val procent1 = 0.0
    sumPrint(perevod, procent1)
    procent1
}

private fun sumPrint(perevod: Double, procent1: Double) {
    println("Всего списано за перевод: ${(perevod * 100.0).roundToInt() / 100.0}")
    println("Сумма комиссии за перевод: ${(procent1 * 100.0).roundToInt() / 100.0}")
}



