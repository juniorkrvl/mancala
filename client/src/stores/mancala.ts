import { ref, computed, type Ref } from "vue";
import { defineStore } from "pinia";

const api = "https://mancala-api.jvnior.com/api/game";

export const useMancalaStore = defineStore("counter", () => {
  const gameId: Ref<string> = ref("");
  const currentPlayer: Ref<number> = ref(0);
  const pits: Ref<number[]> = ref([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
  const firstPlayerMancala: Ref<number> = ref(6);
  const secondPlayerMancala: Ref<number> = ref(13);
  const gameOver: Ref<boolean> = ref(false);
  const error: Ref<string> = ref("");
  const showGameOverModal: Ref<boolean> = ref(false);

  function startNewGame() {
    fetch(api, { method: "POST" })
      .then((response) => response.json())
      .then((data) => {
        updateState(data);
      });
  }

  function play(id: Number, playerId: Number) {
    fetch(api, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        gameId: gameId.value,
        pit: id,
        playerId,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        updateState(data);
      });
  }

  function updateState(data: any) {
    if (data.error?.message) {
      error.value = data.error.message;
      return;
    }

    gameId.value = data.gameId;
    currentPlayer.value = data.currentPlayerId;
    pits.value = data.pits;
    firstPlayerMancala.value = data.firstPlayerMancalaPosition;
    secondPlayerMancala.value = data.secondPlayerMancalaPosition;
    gameOver.value = data.gameOver;
    showGameOverModal.value = data.gameOver;
    error.value = "";
  }

  function cleanError() {
    error.value = "";
  }

  function closeWinnerModal() {
    showGameOverModal.value = false;
  }

  return {
    gameId,
    currentPlayer,
    pits,
    firstPlayerMancala,
    secondPlayerMancala,
    gameOver,
    showGameOverModal,
    error,
    closeWinnerModal,
    startNewGame,
    play,
    cleanError,
  };
});
