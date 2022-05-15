<template>
  <div class="app-container">
    <el-table v-loading="listLoading" default-expand-all style="width: 440px;margin-bottom: 20px;" border
              :data="tableData" row-key="permissionId" :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="名称" sortable width="180">
        <template slot-scope="scope">
          {{ scope.row.permissionTitle }}
          <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
          <el-tag v-if="scope.row.permissionType === '1'" disable-transitions type="warning">按钮</el-tag>
          <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
          <el-tag v-if="scope.row.permissionType === '3'" disable-transitions type="info">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="permissionRouter" label="路由" sortable width="180" />
      <el-table-column fixed="right" label="操作" width="70">
        <template>
          <el-button type="info" icon="el-icon-setting" circle />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import {permissionTreeList} from '@/api/api'

export default {
  data() {
    return {
      tableData: [],
      listLoading: true
    }
  },
  created() {
    this.getPermissionTreeList()
  },
  methods: {
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      console.dir(data)
      this.tableData = data
      // const items = data.items
      // this.list = items.map(v => {
      //   this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
      //   v.originalTitle = v.title //  will be used when user click the cancel botton
      //   return v
      // })
      this.listLoading = false
    }
  }
}
</script>
