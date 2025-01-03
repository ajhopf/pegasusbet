import { AbstractControl, ValidationErrors } from '@angular/forms';

export function minItemsValidator(min: number) {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control || !control.value || !(control.value instanceof Array)) {
      return null; // Não valida se não for um array
    }
    return control.value.length >= min ? null : { minItems: { requiredLength: min, actualLength: control.value.length } };
  };
}
