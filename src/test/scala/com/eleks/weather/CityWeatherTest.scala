package com.eleks.weather

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class CityWeatherTest(_system: ActorSystem)
    extends TestKit(_system)
    with Matchers
    with FlatSpecLike
    with BeforeAndAfterAll {
    //#test-classes

    def this() = this(ActorSystem("AkkaQuickstartSpec"))

    override def afterAll: Unit = {
        shutdown(system)
    }

    "A CityActor" should "reply with empty string if no temperature is known" in {
        val id = 21

        val probe = TestProbe()
        val cityActor = system.actorOf(CityWeather.props("city"))
        cityActor.tell(CityWeather.ReadTemperature(id), probe.ref)
        val response = probe.expectMsgType[CityWeather.RespondTemperature]
        response.requestId should === (id)
        response.value should === (None)
    }

    "A City Actor" should "reply with latest temperature reading" in {
        val ids = Range(0, 4).toList
        val firstTemperature = 21.0
        val secondTemperature = 22.0


        val probe = TestProbe()
        val cityActor = system.actorOf(CityWeather.props("city"))

        cityActor.tell(CityWeather.RecordTemperature(ids(0), firstTemperature), probe.ref)
        probe.expectMsg(CityWeather.TemperatureRecorded(ids(0)))

        cityActor.tell(CityWeather.ReadTemperature(ids(1)), probe.ref)
        val firstResponse = probe.expectMsgType[CityWeather.RespondTemperature]
        firstResponse.requestId should === (ids(1))
        firstResponse.value should === (Some(firstTemperature))

        cityActor.tell(CityWeather.RecordTemperature(ids(2), secondTemperature), probe.ref)
        probe.expectMsg(CityWeather.TemperatureRecorded(ids(2)))

        cityActor.tell(CityWeather.ReadTemperature(ids(3)), probe.ref)
        val secondResponse = probe.expectMsgType[CityWeather.RespondTemperature]
        secondResponse.requestId should === (ids(3))
        secondResponse.value should === (Some(secondTemperature))
    }
}
