<template>
    <div class="home">
      <!-- <h3>wangEditor with vue</h3> -->
      <div id="demo1"></div>
      <!-- <button type="button" class="btn" @click="getEditorData">获取当前内容</button> -->
      <!-- <h3>内容预览</h3>
      <textarea name="" id="" cols="170" rows="20" readonly v-model="editorData"></textarea> -->
    </div>
  </template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue';
import wangEditor from 'wangeditor';
import { useGeneralStore } from '@/stores/general';
const store  = useGeneralStore()

// 使用 ref 代替 data
const editor = ref(null);

onMounted(() => {
  const e = new wangEditor(`#demo1`);
  e.config.onchange = (newHtml) => {
    store.drafts.text = newHtml;
  };
  // 创建编辑器
  e.create();
  // 设置编辑器的初始内容
  e.txt.html(store.drafts.text || ''); // 确保不为 null 或 undefined
  editor.value = e;
});


onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
    editor.value = null;
  }
});

function getEditorData() {
  if (editor.value) {
    let data = editor.value.txt.html();
    alert(data);
  }
}
</script>

<style scoped>
  .home {
    width: 80%;
    max-width: 1200px;
    margin: auto;
    margin-left: 0;
    .btn {
      position: absolute;
      right: 0;
      top: 0;
      padding: 5px 10px;
      cursor: pointer;
    }
    h3 {
      margin: 30px 0 15px;
    }
  }
</style>