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
import { ItemRutinaReq } from './itemRutinaReq';


export interface EntrenoReq { 
    rutinaId: number;
    fecha: string;
    duracionEnSeg: number;
    items: Array<ItemRutinaReq>;
}

