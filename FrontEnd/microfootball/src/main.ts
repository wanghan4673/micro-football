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
import { TUIComponents, TUICore, genTestUserSig } from './TUIKit';
// import TUICallKit
import { TUICallKit } from '@tencentcloud/call-uikit-vue';
import { ref, watch } from 'vue'
import { useGeneralStore } from './stores/general'

const SDKAppID = 1600013711; // Your SDKAppID
const secretKey = '8a1959745a1485707b6fa66ef37a20ccca6ad6e360834fc0b0a3c9c9efd916df'; //Your secretKey

// init TUIKit
const TUIKit = TUICore.init({
  SDKAppID,
});
// TUIKit add TUIComponents
TUIKit.use(TUIComponents);
// TUIKit add TUICallKit
TUIKit.use(TUICallKit);

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(router).use(ElementPlus).use(Antd).use(pinia)
app.use(TUIKit)
app.mount('#app')


const store = useGeneralStore()
let usersig = ref('')
watch(() => store.user.useraccount, (newValue, oldValue) => {
  getUsersig()
  const userID = store.user.useraccount
  // login TUIKit
  TUIKit.login({
    userID: store.user.useraccount,
    userSig: genTestUserSig({
      SDKAppID,
      secretKey,
      userID,  
    }).userSig,  
  
    // userSig: usersig.value,
  });
  console.log("登录账号："+store.user.useraccount)
})

const getUsersig = async () => {
  let response
  let token = localStorage.getItem('token')
  try {
      response = await axios.post('/api/usersig',{
        headers: {
            'token': `${token}`,
            'Content-Type': 'application/json',
        }
    })
      if (response.data.code == 1) {
          console.log("IM登录成功")
          usersig.value = response.data.data.msg
      }
  } catch (error) {
    console.log(error)
  }
}