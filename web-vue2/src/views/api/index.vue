<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <div style="margin-bottom: 20px;">
          <el-input v-model="permissionName" placeholder="请输入名称查询" style="width: 200px;margin-right: 10px;"
                    clearable maxlength="20"
          />
          <el-button type="primary" icon="el-icon-search" circle />
        </div>
        <div class="grid-content bg-purple">
          <el-table v-loading="listLoading" default-expand-all style="width: 95%;margin-bottom: 20px;" border
                    :data="tableData" row-key="permissionId"
                    :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          >
            <el-table-column label="名称" sortable width="170">
              <template v-slot="scope">
                {{ scope.row.permissionTitle }}
                <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
                <el-tag v-if="scope.row.permissionType === '1'" disable-transitions type="warning">按钮</el-tag>
                <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
                <el-tag v-if="scope.row.permissionType === '3'" disable-transitions type="info">其他</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="permissionRouter" label="路由" sortable width="120" />
            <el-table-column fixed="right" label="操作" width="100">
              <template v-slot="scope1">
                <el-button type="text" size="small" @click.native.prevent="deleteRow(scope1.$index, tableData)">
                  设置API
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="15" style="padding-left: 20px;border-left: 1px solid #dedede;">
        <div class="grid-content bg-purple-light">
          <div style="margin-bottom: 20px;">
            <el-button type="danger" round @click="scanApi()">扫描API</el-button>
          </div>
          <div>
            <el-collapse v-model="activeNames">
              <el-collapse-item v-for="group2 in tableData2" :key="group2.apiGroupId"
                                :title="group2.groupName" :name="group2.apiGroupId"
              >
                <el-checkbox-group v-model="checkboxGroup">
                  <el-checkbox v-for="api2 in group2.apiList"
                               :key="api2.apiId" border :label="api2.apiName"
                  />
                </el-checkbox-group>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {getApiList, permissionTreeList, scanApi} from '@/api/api'

export default {
  data() {
    return {
      permissionName: '',
      checkboxGroup: [],
      activeNames: ['1', '2', '3', '4'],
      tableData: [],
      tableData2: [],
      listLoading: true,
      listLoading2: true
    }
  },
  created() {
    this.getPermissionTreeList()
    this.getApiList();
  },
  methods: {
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      this.tableData = data
      this.listLoading = false
    },
    async getApiList() {
      this.listLoading2 = true
      const {data} = await getApiList()
      this.activeNames = [];
      for (const group1 of data) {
        this.activeNames.push(group1.apiGroupId);
      }
      this.tableData2 = data
      this.listLoading2 = false
    },
    async scanApi() {
      await scanApi()
      this.getApiList()
    }
  }
}
</script>
