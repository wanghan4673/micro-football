import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import piniaPersist from 'pinia-plugin-persist'
import Antd from "ant-design-vue"
import axios from 'axios'

import { ref, watch } from 'vue'
import { useGeneralStore } from './stores/general'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(router).use(ElementPlus).use(Antd).use(pinia)
app.mount('#app')