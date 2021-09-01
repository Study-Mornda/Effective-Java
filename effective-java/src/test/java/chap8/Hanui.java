package chap8;

import chap8.classes.Foo;
import chap8.classes.FooAutoCloseable;
import chap8.classes.Zombi;
import chap8.classes.NotZombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see System#gc()
 * finalize 수행을 위해 일부러 gc를 부른다.
 * 단, 이 작업은 강제적 또는 즉시 수행하진 않는다.
 * 따라서 로그의 순서가 달라질 수 있으나, 이게 최선이다.
 * 참고 : https://stackoverflow.com/questions/1481178/how-to-force-garbage-collection-in-java
 */
@DisplayName("Item8. finalizer와 cleaner 사용을 피하라")
public class Hanui {

	@DisplayName("finalizer에서 발생한 예외는 무시된다.")
	@Test
	public void test0(){
		Foo foo = new Foo();
		foo = null;

		System.gc();
		System.out.println("예외는 무시됩니다.");
	}

	@DisplayName("finalizer(cleaner)는 심각한 성능저하가 있다.")
	@Test
	public void test1() throws Exception {
		// try-with-resources 사용
		long start = System.nanoTime();
		try (FooAutoCloseable ac = new FooAutoCloseable()){
			//AutoCloseable 구현에 의해 자동으로 닫아짐
		}
		long end = System.nanoTime();
		System.out.println("gc 수거: "+(end-start)/1000000.0+ "ms");

		// finalizer 사용
		start = System.nanoTime();
		FooAutoCloseable ac = new FooAutoCloseable();
		ac=null;
		System.gc();
		end = System.nanoTime();
		System.out.println("finalizer 사용: "+ (end-start)/1000000.0 + "ms");
	}

	// 참고 : https://yangbongsoo.tistory.com/8
	@DisplayName("finalize 공격: 정적 필드에 자기 참조를 하면 여전히 참조 가능하다")
	@Test
	public void test2(){
		NotZombie beCleaned = new NotZombie();
		Zombi notBeCleaned = new Zombi();

		beCleaned = null;
		notBeCleaned= null;
		System.gc();

		System.out.println(beCleaned);
		System.out.println(notBeCleaned.zombi);
		assertThat(beCleaned).isNull();
		assertThat(notBeCleaned).isNull();
		assertThat(notBeCleaned.zombi).isNotNull();
	}

	@DisplayName("finalize 공격:")
	@Test
	public void test2_2(){

	}
	@DisplayName("cleaner, finalizer 쓰임1: close메서드 미호출에 대한 안전망 역할")
	@Test
	public void test3(){

	}

	// @DisplayName()
	@Test
	public void test3_2(){

	}


}