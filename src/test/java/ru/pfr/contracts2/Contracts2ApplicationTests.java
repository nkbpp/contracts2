package ru.pfr.contracts2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//В следующей таблице приведен обзор имеющихся в аннотации JUnit 4.x.

//Аннотация 	                        Описание
    /*@Test
    public void method() 	                Аннотация @Test определяет что метод method() является тестовым.*/
    /*@Before
    public void method() 	                Аннотация @Before указывает на то, что метод будет выполнятся
                                            перед каждым тестируемым методом @Test.*/
    /*@After
    public void method() 	                Аннотация @After указывает на то что метод будет выполнятся
                                            после каждого тестируемого метода @Test*/
    /*@BeforeClass
    public static void method() 	        Аннотация @BeforeClass указывает на то, что метод будет
                                            выполнятся в начале всех тестов, а точней в момент запуска
                                            тестов(перед всеми тестами @Test).*/
    /*@AfterClass
    public static void method() 	        Аннотация @AfterClass  указывает на то, что метод будет
                                            выполнятся после всех тестов.*/
    /*@Ignore 	                            Аннотация @Ignore говорит, что метод будет проигнорирован в
                                            момент проведения тестирования.*/
    /*@Test (expected = Exception.class)    (expected = Exception.class) – указывает на то, что в данном
                                            тестовом методе вы преднамеренно ожидается Exception.*/
    /*@Test (timeout=100) 	                (timeout=100) – указывает, что тестируемый метод не должен
                                            занимать больше чем 100 миллисекунд.*/

//Проверяемые методы (основные)

//Метод 	                                         Описание
    /*fail(String) 	                                     Указывает на то что бы тестовый метод завалился при
                                                         этом выводя текстовое сообщение.*/

/*assertTrue([message], boolean condition) 	         Проверяет, что логическое условие истинно.*/

    /*assertsEquals([String message], expected, actual)  Проверяет, что два значения совпадают.
                                                         Примечание: для массивов проверяются ссылки, а не
                                                         содержание массивов.*/

/*assertNull([message], object)                      Проверяет, что объект является пустым null.*/
/*assertNotNull([message], object) 	                 Проверяет, что объект не является пустым null.*/
    /*assertSame([String], expected, actual) 	         Проверяет, что обе переменные относятся к одному
                                                         объекту.*/
    /*assertNotSame([String], expected, actual) 	     Проверяет, что обе переменные относятся к разным
                                                         объектам.*/
    /*assertArrayEquals                                  Функция для сравнения массивов, которая
                                                         сравнивает эквивалентность каждого элемента обоих
                                                         массивов друг с другом*/

@SpringBootTest
class Contracts2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
