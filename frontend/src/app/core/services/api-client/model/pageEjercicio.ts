/**
 * MyFitnessApp
 * A REST api for exercise and workout tracking
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PageableObject } from './pageableObject';
import { Ejercicio } from './ejercicio';
import { SortObject } from './sortObject';


export interface PageEjercicio { 
    totalPages?: number;
    totalElements?: number;
    size?: number;
    content?: Array<Ejercicio>;
    number?: number;
    sort?: Array<SortObject>;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    pageable?: PageableObject;
    empty?: boolean;
}

