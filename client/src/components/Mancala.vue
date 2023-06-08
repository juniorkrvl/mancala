<template>
  <div
    class="mancala"
    @mouseover="showStoneAmount = true"
    @mouseleave="showStoneAmount = false"
  >
    <div class="player-number">P {{ playerId }}</div>
    <div v-if="showStoneAmount" class="mancala-stone-amount">
      {{ stones }}
    </div>
    <div class="mancala-layer">
      <Stone
        v-for="(stone, idx) in stones"
        :id="idx"
        :key="stone"
        :player-id="playerId"
        :total-stones-in-pit="stones"
      />
    </div>
  </div>
</template>

<script setup>
import Stone from "@/components/Stone.vue";
import { ref } from "vue";

defineProps({
  playerId: Number,
  stones: Number,
});

const showStoneAmount = ref(false);
</script>

<style scoped>
.mancala {
  height: 80%;
  width: 80%;
  padding: 15px;
  box-sizing: border-box;
  border-radius: 40px;
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
    url("/wooden-board.jpg");
  text-transform: uppercase;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  position: absolute;
  z-index: 2;
}

.mancala-layer {
  display: flex;
  padding: 15px;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: row;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 3;
}

.mancala-stone-amount {
  z-index: 4;
  text-align: center;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  cursor: pointer;
}

.player-number {
  position: absolute;
  visibility: hidden;
  animation-name: player-appear;
  animation-duration: 4s;
}

@keyframes player-appear {
  0% {
    visibility: visible;
  }

  30% {
    transform: scale(2);
    opacity: 1;
  }

  100% {
    opacity: 0;
  }
}
</style>
