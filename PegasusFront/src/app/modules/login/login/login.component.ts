import { Component } from '@angular/core'
import { FormControl, FormGroup } from '@angular/forms'
import { AuthService } from '../../../services/auth/auth.service'
import { take } from 'rxjs'
import { CookieService } from 'ngx-cookie-service'
import { LoginResponse } from '../../../models/auth/LoginResponse'
import { MessageService } from 'primeng/api'
import { Router } from '@angular/router'
import { TokenFields } from '../../../models/auth/TokenFields'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  createUserMode: boolean = false

  constructor(
    private authService: AuthService,
    private cookieService: CookieService,
    private messageService: MessageService,
    private router: Router
  ) {
  }

  loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    }
  )

  handleChangeForm(): void {
    this.createUserMode = !this.createUserMode
  }

  handleFormSubmit() {
    const {username, password} = this.loginForm.value

    if (username && password) {
      if (this.createUserMode) {
        this.authService.createUser(username, password)
          .pipe(take(1))
          .subscribe({
            next: response => {
              this.messageService.add({
                severity: 'success',
                summary: 'Usuário criado com sucesso',
                detail: 'Você já pode fazer o seu login'
              })
              this.createUserMode = false
            },
            error: err => {
              this.messageService.add({
                severity: 'error',
                summary: 'Infelizmente não foi possível criar o usuário',
                detail: 'Tente novamente ou fale com um administrador do sistema'
              })
            }
          })


      } else {
        this.authService.login(username, password)
          .pipe(take(1))
          .subscribe({
            next: response => this.handleSuccessfulLogin(response),
            error: err => this.handleError(err)
          })
      }

    }
  }

  handleSuccessfulLogin(response: LoginResponse) {
    this.cookieService.delete(TokenFields.USER_ROLE)
    this.cookieService.delete(TokenFields.USERNAME)
    this.cookieService.delete(TokenFields.ACCESS_TOKEN)

    this.cookieService.set(TokenFields.ACCESS_TOKEN, response.access_token)
    this.cookieService.set(TokenFields.USER_ROLE, response.roles[0])
    this.cookieService.set(TokenFields.USERNAME, response.username)

    this.loginForm.reset()

    if (response.roles[0] == 'ROLE_ADMIN') {
      this.router.navigate(['/admin'])
    } else {
      this.router.navigate(['/races'])
    }

    this.messageService.add({
      severity: 'success',
      summary: 'Sucesso',
      detail: `Bem vindo de volta ${response.username}!`,
      life: 2000
    })
  }

  handleError(err: any) {
    this.messageService.add({
      severity: 'error',
      summary: 'Erro',
      detail: `Erro ao fazer o login!`,
      life: 2000
    })
    console.log(err)
  }

}
