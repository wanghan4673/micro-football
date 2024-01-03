<template>
  <div v-if="selected" :style="Background" @click="recoverBackground">
    <img :src="img" class="img-element">
    <div class="text">{{ buttonName }}</div>
  </div>
  <div v-else :style="Background" @click="changeBackground">
    <img :src="img" class="img-element">
    <div class="text">{{ buttonName }}</div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';

export default {
  props: {
    imgurl: String,
    initbackground: String,
    buttonname: String,
  },

  setup(props, { emit }) {
    const name = props.buttonname
    const selected = ref(false);
    const img = ref(props.imgurl);
    const buttonName = ref(props.buttonname);
    const Background = ref({
      background: props.initbackground,
    });

    const changeBackground = () => {
      selected.value = true;
      Background.value.background = 'radial-gradient(circle, #66ccff, #800080)';
      emit('select-event', name);
    };

    const recoverBackground = () => {
      selected.value = false;
      Background.value.background = props.initbackground;
      emit('recover-event', name);
    };

    return {
      img,
      changeBackground,
      recoverBackground,
      Background,
      buttonName,
      selected,
    };
  },
};
</script>

<style scoped>
.img-element {
  width: 100px;
  height: 75px;
}

.text{
    display:flex;
    font-weight:bold;
    color: #010c34;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
    align-items:center;
    justify-content: center;

}
</style>


