<template>
  <div
    :id="id"
    class="hole-1"
    @click.stop="click"
    @mouseover="showStoneAmount = true"
    @mouseleave="showStoneAmount = false"
  >
    <div class="stone-layer">
      <Stone
        v-for="(stone, idx) in stones"
        :id="idx"
        :key="stone"
        :player-id="playerId"
        :total-stones-in-pit="stones"
      />
      <div v-if="showStoneAmount" class="hover-stone-amount">{{ stones }}</div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import Stone from "@/components/Stone.vue";

const props = defineProps({
  id: Number,
  playerId: Number,
  stones: Number,
});

const showStoneAmount = ref(false);

const emit = defineEmits();

function click(event) {
  event.stopImmediatePropagation();
  emit("click", props.id, props.playerId);
}
</script>

<style scoped>
.stone-layer {
  width: 100%;
  height: 100%;
  display: flex;
  padding: 15px;
  box-sizing: border-box;
  cursor: pointer;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: row;
  position: relative;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 2;
}

.stone {
  border-radius: 50%;
  width: 30px;
  height: 30px;
  background-color: pink;
  background: radial-gradient(#b400a2, #9e0031);
  box-shadow: 0 3px 2px rgba(0, 0, 0, 0.3);
}

.hover-stone-amount {
  z-index: 3;
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
</style>
