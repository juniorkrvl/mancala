<template>
  <MessageModal :open="open" @ok="$emit('close')">
    <h2>How to play</h2>
    <h3>Board Setup</h3>
    <p>
      Each of the two players has his six pits in front of him. To the right of
      the six pits, each player has a larger pit. At the start of the game,
      there are six stones in each of the six round pits .
    </p>
    <h2>Rules</h2>
    <h3>Game Play</h3>
    <p>
      The player who begins with the first move picks up all the stones in any
      of his own six pits, and sows the stones on to the right, one in each of
      the following pits, including his own big pit. No stones are put in the
      opponents' big pit. If the player's last stone lands in his own big pit,
      he gets another turn. This can be repeated several times before it's the
      other player's turn.
    </p>
    <h3>Capturing Stones</h3>
    <p>
      During the game the pits are emptied on both sides. Always when the last
      stone lands in an own empty pit, the player captures his own stone and all
      stones in the opposite pit (the other player’s pit) and puts them in his
      own big pit.
    </p>
    <h3>The Game Ends</h3>
    <p>
      The game is over as soon as one of the sides runs out of stones. The
      player who still has stones in his pits keeps them and puts them in his
      big pit. The winner of the game is the player who has the most stones in
      his big pit.
    </p>
  </MessageModal>
</template>

<script setup>
import MessageModal from "@/components/MessageModal.vue";

defineProps({
  open: Boolean,
});

defineEmits(["close"]);
</script>
