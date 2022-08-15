<template>
    <div>
        <v-progress-linear
        indeterminate
        color="primary"
        v-if="loadingPage"
        ></v-progress-linear>
        <v-container>
            <v-row>
                <v-col>
                    <h1 class="mb-2">Exemplos</h1>
                </v-col>
                <v-col >
                    <v-dialog 
                    v-model="dialog"
                    persistent
                    max-width="500px"
                    >
                        <template v-slot:activator="{ on, attrs }">
                            <v-btn class="float-right"
                            color="primary"
                            dark
                            v-bind="attrs"
                            v-on="on"
                            @click="novo"
                            >
                                <v-icon dark>add</v-icon>
                                Adicionar
                            </v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                            <span class="text-h5">Exemplo</span>
                            </v-card-title>
                            <v-divider/>
                            <v-progress-linear
                            indeterminate
                            color="primary"
                            v-if="loading"
                            ></v-progress-linear>
                            <v-card-text>
                                <v-form ref="formExemplo" v-model="valid" lazy-validation>
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12">
                                                <v-text-field
                                                label="Nome *"
                                                v-model="exemplo.nome"
                                                :rules="[() => !!exemplo.nome || 'Campo obrigatório!']"
                                                ></v-text-field>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-form>
                                <small>* Campos Obrigatórios</small>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn
                                color="blue darken-1"
                                text
                                @click="dialog = false"
                                >
                                    Fechar
                                </v-btn>
                                <v-btn
                                color="blue darken-1"
                                text
                                @click="salvar()"
                                >
                                    Salvar
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-col>
            </v-row>

            <v-data-table
                :headers="headers"
                :items="exemplos"
                :items-per-page="10"
                class="elevation-1"
                no-data-text="Nenhum exemplo cadastrado."
                :disable-sort="true"
            >
                <template v-slot:[`item.actions`]="{ item }">
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                            <v-btn icon v-on="on">
                                <v-icon small color="blue" @click="editar(item)">edit</v-icon>
                            </v-btn>
                        </template>
                        <span>Editar</span>
                    </v-tooltip>                
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                            <v-btn icon v-on="on">
                                <v-icon small color="red" @click="remover(item)">delete</v-icon>
                            </v-btn>
                        </template>
                        <span>Remover</span>
                    </v-tooltip>
                </template>
            </v-data-table>

            <v-snackbar
                v-model="snackbar.active"
                :color="snackbar.color"
                :bottom="true"
                >{{snackbar.message}}
                
                <template v-slot:action="{ attrs }">
                    <v-btn
                        color="white"
                        text
                        v-bind="attrs"
                        @click="snackbar.active = false"
                    >
                        Fechar
                    </v-btn>
                </template>
            </v-snackbar>
                
            <!-- CONFIRM DIALOG -->
            <v-dialog v-model="confirmDialog" max-width="500" persistent>
                <v-card>
                    <v-card-title class="headline">Remover</v-card-title>
                    <v-divider />
                    <v-card-text class="mt-3">Você deseja remover o {{exemplo.nome}}?</v-card-text>
                    <v-card-actions>
                        <div class="flex-grow-1"></div>
                        <v-btn color="error" text @click="confirmDialog=false">CANCELAR</v-btn>
                        <v-btn color="green darken-1" text @click="confirmaRemover">ACEITAR</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-container>
    </div>
</template>

<script>
import ExemploService from '@/services/ExemploService'

export default {  
  name: 'ExemploForm',

  data() {
    return {
      headers: [
        { text: 'Código', value: 'id', width: "150px" },
        { text: 'Nome', value: 'nome' },
        {
            text: "Ações",
            value: "actions",
            align: "center",
            sortable: false,
            width: "150px"
        }
      ],
      exemplos: [],
      dialog: false,
      exemplo: {},
      loading: false,
      valid: true,
      confirmDialog: false,
      loadingPage: false,
      snackbar: {
        message: "",
        color: "error",
        active: false
      },
    }
  },
  methods: {
    editar(exemplo) {
        this.exemplo = {...exemplo};
        this.openModal();
    },
    remover(exemplo) {
        this.exemplo = {...exemplo};
        this.confirmDialog = true;
    },
    async confirmaRemover() {
        try {
            this.loadingPage = true;
            await ExemploService.remover(this.exemplo.id);
            this.mostrarMensagem('Exemplo removido com sucesso!', 'green');
            await this.listar();
        } catch (err) {
            console.error(err)
            this.mostrarMensagem(`Exemplo ao tentar remover o exemplo!`, 'red');
        } finally {
            this.loadingPage = false;
            this.confirmDialog = false;
        }
    },
    async salvar() {
        this.$refs.formExemplo.validate()
        if (this.valid) {
            let isCadastrar = false;
            try {
                this.loading = true;
                if (this.exemplo.id) {
                    await ExemploService.atualizar(this.exemplo.id, this.exemplo);
                    this.mostrarMensagem('Exemplo atualizado com sucesso!', 'green');
                } else {
                    isCadastrar = true;
                    await ExemploService.cadastrar(this.exemplo);
                    this.mostrarMensagem('Exemplo cadastrado com sucesso!', 'green');
                }
                await this.listar();
                
                this.dialog = false;
            } catch (err) {
                console.error(err)
                this.mostrarMensagem(`Exemplo ao tentar ${isCadastrar ? 'cadastrar' : 'atualizar'} o exemplo!`, 'red');
            } finally {
                this.loading = false;
            }
        }
    },
    novo() {
        this.openModal();
        this.exemplo = {}
    },
    async listar() {
        const response = await ExemploService.listar();
        this.exemplos = response.data;
    },
    openModal() {
        this.valid = true;
        this.dialog = true;
    },
    mostrarMensagem(message, color) {
      this.snackbar.message = message;
      this.snackbar.color = color;
      this.snackbar.active = true;
    }
  },
  created() {
    this.listar();
  }
}
</script>