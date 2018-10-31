package sloydev.com.androidperformancetest

import dagger.Component

@Component(modules = [KotlinDaggerModule::class])
interface KotlinDaggerComponent {
    fun inject(injectionTest: InjectionTest.KotlinDaggerTest)
}

@Component(modules = [JavaDaggerModule::class])
interface JavaDaggerComponent {
    fun inject(injectionTest: InjectionTest.JavaDaggerTest)
}