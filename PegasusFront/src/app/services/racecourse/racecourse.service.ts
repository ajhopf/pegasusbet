import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { TokenFields } from "../../models/auth/TokenFields";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { CookieService } from "ngx-cookie-service";
import { Observable } from "rxjs";
import { GetRacesResponse } from "../../models/races/GetRacesResponse";
import { GetRacecoursesResponse } from "../../models/race-course/GetRacecoursesReponse";
import { CreateRacecourseRequest } from '../../models/race-course/CreateRacecourseRequest'
import { CreateRacecourseResponse } from '../../models/race-course/CreateRacecourseResponse'

@Injectable({
  providedIn: 'root'
})
export class RacecourseService {

  private API_URL = environment.DATASTORE_API;

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(private httpClient: HttpClient, private cookie: CookieService) { }

  fetchRacecourses(offset: number, max: number): Observable<GetRacecoursesResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/racecourses?offset=${offset}&max=${max}`, this.httpOptions
      )
  }

  createRacecourse(racecourseRequest: CreateRacecourseRequest): Observable<CreateRacecourseResponse> {
    return this.httpClient.post<any>(`${this.API_URL}/racecourses`,
      racecourseRequest,
      this.httpOptions)
  }
}
