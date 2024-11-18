package me.choicore.samples.charge.domain.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimelineTests {
    @Test
    fun `should add a new time slot without throwing an exception`() {
        val timeline = Timeline()
        timeline.addSlot(LocalTime.of(9, 0), LocalTime.of(17, 0))

        assertThatNoException().isThrownBy {
            timeline.addSlot(LocalTime.of(17, 0), LocalTime.of(19, 0))
        }
    }

    @Test
    fun `should throw an exception when adding overlapping time slot`() {
        val timeline: Timeline =
            Timeline()
                .apply {
                    this.addSlot(LocalTime.of(9, 0), LocalTime.of(17, 0))
                }

        assertThatThrownBy {
            timeline.addSlot(LocalTime.of(16, 59), LocalTime.of(19, 0))
        }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `should not overlap when timelines have no overlapping slots`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(11, 0), LocalTime.of(12, 0))
            }

        assertThat(timeline.overlapsWith(other)).isFalse
        assertThat(other.overlapsWith(timeline)).isFalse
    }

    @Test
    fun `should overlap when timeline's previous slot overlaps`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(10, 0), LocalTime.of(11, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 30), LocalTime.of(10, 30))
            }

        assertThat(timeline.overlapsWith(other)).isTrue
        assertThat(other.overlapsWith(timeline)).isTrue
    }

    @Test
    fun `should overlap when timeline's current slot overlaps`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 30), LocalTime.of(10, 30))
            }

        assertThat(timeline.overlapsWith(other)).isTrue
        assertThat(other.overlapsWith(timeline)).isTrue
    }

    @Test
    fun `should overlap when timeline's next slot overlaps`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(10, 0), LocalTime.of(11, 0))
            }

        assertThat(timeline.overlapsWith(other)).isFalse
        assertThat(other.overlapsWith(timeline)).isFalse
    }

    @Test
    fun `should handle multiple slots with some overlapping and some not`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(8, 0), LocalTime.of(9, 0))
                this.addSlot(LocalTime.of(11, 0), LocalTime.of(12, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
                this.addSlot(LocalTime.of(10, 30), LocalTime.of(11, 30))
            }

        assertThat(timeline.overlapsWith(other)).isTrue
        assertThat(other.overlapsWith(timeline)).isTrue
    }

    @Test
    fun `should overlap when timelines have the same specified date`() {
        val timeline: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
            }

        val other: Timeline =
            Timeline().apply {
                this.addSlot(LocalTime.of(9, 30), LocalTime.of(10, 30))
            }

        assertThat(timeline.overlapsWith(other)).isTrue
        assertThat(other.overlapsWith(timeline)).isTrue
    }
}
