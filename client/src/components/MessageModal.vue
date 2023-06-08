<template>
  <div v-if="open" id="winning-modal" style="visibility: visible">
    <div id="winning-message">
      <slot></slot>
      <!-- <p id="winner">Player 2 is the winner!</p>
      <p>Would you like to play again?</p> -->
      <div v-if="yesNo" class="buttons">
        <button class="button yes" @click="$emit('yes', $event)">Yes</button>
        <button class="button no" @click="$emit('no', $event)">No</button>
      </div>
      <div v-else class="buttons">
        <button class="button ok" @click="$emit('ok', $event)">Ok</button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  open: Boolean,
  yesNo: Boolean,
});
</script>

<style scoped>
#winning-modal {
  position: absolute;
  left: 0px;
  top: 0px;
  width: 100%;
  height: 100%;
  text-align: center;
  z-index: 5000;
  background-color: rgba(0, 0, 0, 0.4);
}

#winning-message {
  width: 500px;
  margin: 100px auto;
  background-color: #fff;
  border: 1px solid #000;
  padding: 15px;
  text-align: center;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.2);
  font-family: "Tex Gyre Adventor", sans-serif;
}

button {
  background-color: #000da4;
  color: white;
  text-transform: uppercase;
  margin: 0 20px;
  cursor: pointer;
  border: none;
  font-size: 14px;
  padding: 10px;
  border-radius: 5px;
}
</style>
