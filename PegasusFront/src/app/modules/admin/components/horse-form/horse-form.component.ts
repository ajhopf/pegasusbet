import { Component } from '@angular/core';
import { FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html'
})

export class HorseFormComponent {

  createHorseForm = this.formBuilder.group({
    name: [null, Validators.required],
    sex: [null, Validators.required],
    age: [null, Validators.required],
    numberOfRaces: [null, Validators.required],
    numberOfVictories: [null, Validators.required],
    results: [[], [Validators.required, this.validateResult]],
  })


  constructor(private formBuilder: FormBuilder) {}

  handleSubmit() {
    console.log(this.createHorseForm)
  }

  validateResult (event:any) {
    const results: string[] = event.value

    results.forEach(result => {
      const valid: boolean = /^(\d+)\/(\d+)$/.test(result)

      if (!valid) {
        return
      }
    })

  }

}
