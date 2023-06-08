<template>
  <div class="scoreboard">
    <div class="score-row"><strong>Scores</strong></div>
    <div class="score-row">
      Player 1 → {{ firstPlayerScore }}
      <span v-if="firstPlayerScore > secondPlayerScore">🏆</span>
    </div>
    <div class="score-row">
      Player 2 → {{ secondPlayerScore }}
      <span v-if="secondPlayerScore > firstPlayerScore">🏆</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  firstPlayerScore: Number,
  secondPlayerScore: Number,
});
</script>

<style scoped>
.scoreboard {
  margin: auto;
  border: 1px solid #000;
  padding: 10px;
  width: 200px;
  margin-bottom: 30px;
}

.score-row {
  margin-bottom: 10px;
}
</style>
