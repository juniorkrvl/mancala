<template>
  <div class="stone" :style="style"></div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  id: Number,
  playerId: Number,
  totalStonesInPit: Number,
});

const style = computed(() => {
  const center = 50;

  const gradient =
    props.playerId == 1
      ? "radial-gradient(circle, rgba(155,217,254,1) 0%, rgba(49,49,238,1) 100%)"
      : "radial-gradient(circle,rgba(59, 59, 232, 1) 0%,rgba(9, 9, 121, 1) 100%)";

  // Calculate the radius and angle for this stone
  const radius = Math.sqrt(props.id / props.totalStonesInPit) * 40; // This will give a number between 0 and 40
  const angle = (props.id / props.totalStonesInPit) * 2 * Math.PI; // This will distribute the stones evenly around the circle

  let left = center + radius * Math.cos(angle);
  let top = center + radius * Math.sin(angle);

  var randomOffset = (Math.random() - 0.5) * 20; // This will give a number between -5 and 5
  left += randomOffset;
  top += randomOffset;

  return {
    background: gradient,
    top: top + "%",
    left: left + "%",
  };
});
</script>

<style scoped>
.stone {
  border-radius: 50%;
  width: 30px;
  height: 30px;
  background-color: pink;
  background: radial-gradient(#b400a2, #9e0031);
  box-shadow: 0 3px 2px rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* center the stone */
}
</style>
