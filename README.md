### В ході практичного ви маєте опанувати наступні технології:

* Maven
* Git
* GitHub
* JUnit

### Ваше завдання:

Уявимо, що ви розробляєте клієнт серверне застосування, що має обмінюватися повідомленнями по мережі. 
Ваші дані містять комерційну таємницю і не можуть передаватися в відкритому вигляді. 
Тому ви маєте розробити протокол обміну повідомленнями. 

**Таким чином структура нашого пакету:**

| Offset | Length | Mnemonic | Notes                                                                 | 
|--------|--------|----------|-----------------------------------------------------------------------|
| 00     | 1      | bMagic   | Байт, що вказує на початок пакету - значення 13h (h - значить hex)    |
| 01     | 1      | bSrc     | Унікальний номер клієнтського застосування                            |
| 02     | 8      | bPktId   | Номер повідомлення. Номер постійно збільшується. В форматі big-endian |
| 10     | 4      | wLen     | Довжина пакету даних big-endian                                       |
| 14     | 2      | wCrc16   | CRC16 байтів (00-13) big-endian                                       |
| 16     | wLen   | bMsq     | Message - корисне повідомлення                                        |
| 16     | 2      | wCrc16   | CRC16 байтів (16 до 16+wLen-1) big-endian                             |

**Структура повідомлення (message)**

| Offset | Length | Mnemonic | Notes                                                                                                                                                    |
|--------|--------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| 00     | 4      | cType    | Код команди big-endian                                                                                                                                   |
| 04     | 4      | bUserId  | Від кого надіслане повідомлення. В системі може бути багато клієнтів. А на кожному з цих клієнтів може працювати один з багатьох працівників. big-endian |
| 08     | wLen-8 | message  | корисна інформація, можна покласти JSON як масив байтів big-endian                                                                                       |

### Ваша задача:

* Встановити Java версія не нижче 8
* Встановити Maven https://maven.apache.org/install.html
* Встановити Git
* Зареєструватися на Bitbacket? GitLab або GitHub. Створити private репозиторій (не забудьте про .gitignore)
* Створити maven проект та приєднати JUnit
https://www.mkyong.com/maven/how-to-create-a-java-project-with-maven/
* Реалізувати клас, що вміє отримувати вище перерахований пакет у вигляді масиву байтів, перевіряти його на коректність (перевіряти правильність CRC16), зберігати дані передані в пакеті, дешифрувати повідомлення (message). Інформація отримана з пакета має бути дешиврована в POJO об'єкт
* Реалізувати клас, що вміє створювати повідомлення в відповідному форматі (зворотний пункт до попереднього)
* Тіло повідомлення (message) має бути зашифроване
http://tutorials.jenkov.com/java-cryptography/cipher.html
* Реалізувати клас тестувальник, що перевірить правильність вашого коду
* Отриманий код залити в віддалений репозиторій
* Зробити скріншот останніх комітів
* Залити архів коду вашого проекту та скріншот на дістеду

###  Корисні лінки
* [CRC16](https://introcs.cs.princeton.edu/java/61data/CRC16.java)
* [Big-Endian](https://uk.wikipedia.org/wiki/%D0%9F%D0%BE%D1%80%D1%8F%D0%B4%D0%BE%D0%BA_%D0%B1%D0%B0%D0%B9%D1%82%D1%96%D0%B2 )