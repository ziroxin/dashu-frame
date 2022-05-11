<template>
  <div class="dashboard-container">
    <!--    <component :is="currentRole" />-->
    {{ currentRole }}
  </div>
</template>

<script>
// import adminDashboard from './admin'
// import editorDashboard from './editor'
export default {
  name: 'Dashboard',
  // components: { adminDashboard, editorDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
  }
}
</script>
