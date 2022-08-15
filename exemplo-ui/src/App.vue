<template>
  <v-app>
    <v-app-bar
      app
      color="#011b42"
      dark
      height="70"
    >
      <div>
        <img style="cursor: pointer; margin-left: -15px; margin-top: 10px; width: 220px; height: 80px" src="./assets/CemiCloud.png"/>
      </div>

    </v-app-bar>

    <v-main>
      <ExemploForm/>
    </v-main>
  </v-app>
</template>

<script>
import ExemploForm from './views/cadastros/ExemploForm';

export default {
  name: 'App',

  components: {
    ExemploForm,
  },

  data: () => ({
    //
  }),
};
</script>
