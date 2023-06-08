<template>
  <div id="wrapper">
    <header>
      <nav>
        <div>
          <h1>Mancala</h1>
        </div>
        <div>
          <button @click="startNewGame">🎮 Start New Game</button>
          <button @click="showHowToPlayModal">📚 How to Play</button>
        </div>
      </nav>
    </header>
    <main>
      <span id="game-id">Game ID: {{ store.gameId }}</span>
      <Board
        v-if="store.pits?.length > 0"
        :pits="store.pits"
        :first-player-mancala="store.firstPlayerMancala"
        :second-player-mancala="store.secondPlayerMancala"
        :current-player="store.currentPlayer"
        @pit-selected="pitSelected"
      />
    </main>
    <ScoreBoard
      :first-player-score="firstPlayerScore"
      :second-player-score="secondPlayerScore"
    />
    <!-- Error Modal -->
    <MessageModal :open="store.error?.length > 0" @ok="store.cleanError()">
      <p>{{ store.error }}</p>
    </MessageModal>
    <!-- Winning Modal -->
    <MessageModal
      :open="store.showGameOverModal"
      @yes="store.startNewGame()"
      @no="store.closeWinnerModal()"
      yes-no
    >
      <p>
        The player {{ winner }} won the match with
        {{ winner == 1 ? firstPlayerScore : secondPlayerScore }} points!
      </p>
      <p>Do you want to play again?</p>
    </MessageModal>
    <HowToPlayModal :open="howToPlay" @close="howToPlay = false" />
    <footer>Created by Junior Carvalho</footer>
  </div>
</template>

<script setup>
import Board from "@/components/Board.vue";
import MessageModal from "@/components/MessageModal.vue";
import HowToPlayModal from "@/views/HowToPlayModal.vue";
import ScoreBoard from "@/components/ScoreBoard.vue";
import { useMancalaStore } from "@/stores/mancala";
import { computed, onMounted, ref } from "vue";

const howToPlay = ref(false);

const store = useMancalaStore();

const firstPlayerScore = computed(() => {
  return store.pits[store.firstPlayerMancala];
});

const secondPlayerScore = computed(() => {
  return store.pits[store.secondPlayerMancala];
});

const winner = computed(() => {
  return store.pits[store.firstPlayerMancala] >
    store.pits[store.secondPlayerMancala]
    ? 1
    : 2;
});

function pitSelected(pit, player) {
  if (store.gameId) {
    store.play(pit, player);
  }
}

function startNewGame() {
  store.startNewGame();
}

function showHowToPlayModal() {
  howToPlay.value = true;
}
</script>

<style scoped>
#game-id {
  padding: 10px;
}

#wrapper {
  width: 1250px;
  margin: 40px auto;
  /* border: 1px solid #000da4; */
  font-family: "Tex Gyre Adventor", sans-serif;
}

main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

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

nav {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  background-color: #000da4;
  color: white;
  padding-left: 25px;
  text-transform: uppercase;
  letter-spacing: 2px;
  border-radius: 7px;
}

h1 {
  font-size: 40px;
  margin: 10px 20px 10px 0;
  font-weight: normal;
}

footer {
  height: 50px;
  background-color: #000da4;
  border-radius: 7px;
  letter-spacing: 2px;
  color: white;
  text-transform: uppercase;
  font-size: 9px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  margin-top: 20px;
}

#close-button {
  display: flex;
  justify-content: flex-end;
  flex-direction: row;
  align-items: center;
}
</style>
