<template>
  <div id="player-1">
    ← Player 2 👤<span v-if="currentPlayer == 2">🎲</span>
  </div>

  <div id="board">
    <div id="col-1">
      <Mancala :stones="pits[secondPlayerMancala]" :player-id="2" />
    </div>

    <div id="col-2">
      <div id="row-1">
        <Pit
          v-for="(stones, idx) in secondPlayerRange"
          :stones="pits[secondPlayerMancala - 1 - idx]"
          :id="secondPlayerMancala - 1 - idx"
          :player-id="2"
          @click="pitSelected"
        />
      </div>
      <div id="row-2">
        <Pit
          v-for="(stones, idx) in firstPlayerRange"
          :stones="stones"
          :id="idx"
          :player-id="1"
          @click="pitSelected"
        />
      </div>
    </div>

    <div id="col-3">
      <Mancala :stones="pits[firstPlayerMancala]" :player-id="1" />
    </div>
  </div>

  <div id="player-2">
    <span v-if="currentPlayer == 1" title="Player's turn">🎲</span>👤 Player 1 →
  </div>
</template>

<script setup>
import Pit from "@/components/Pit.vue";
import Mancala from "@/components/Mancala.vue";
import { computed } from "vue";

const props = defineProps({
  pits: Array,
  firstPlayerMancala: Number,
  secondPlayerMancala: Number,
  currentPlayer: Number,
});

const emit = defineEmits(["pit-selected"]);

const firstPlayerRange = computed(() => {
  return props.pits.slice(0, props.firstPlayerMancala);
});

const secondPlayerRange = computed(() => {
  return props.pits.slice(
    props.firstPlayerMancala + 1,
    props.secondPlayerMancala
  );
});

function pitSelected(id, playerId) {
  emit("pit-selected", id, playerId);
}
</script>

<style scoped>
button {
  background-color: #000da4;
  color: white;
  text-transform: uppercase;
  margin: 0 20px;
  cursor: pointer;
  border: none;
  font-size: 14px;
}

main > div {
  width: 95%;
  height: 350px;
}

#player-1,
#player-2 {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  padding-bottom: 10px;
  box-sizing: border-box;
  text-transform: uppercase;
  letter-spacing: 2px;
}

#player-2 {
  align-items: flex-start;
  padding-top: 10px;
}

#board {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  background-image: url("/wooden-board.jpg");
  border-radius: 20px;
}

#board > div {
  height: 100%;
}

#col-1,
#col-3 {
  width: 15%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  z-index: 1;
}

#col-2 {
  width: 70%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#row-1,
#row-2 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.hole-1,
.hole-2 {
  border-radius: 50%;
  width: 115px;
  height: 115px;
  position: relative;
  margin: 10px;
  box-sizing: border-box;
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
    url("wooden-board.jpg");
  padding: 15px;
  z-index: 1;
}

span {
  font-family: "Tex Gyre Adventor Bold", sans-serif;
}
</style>
