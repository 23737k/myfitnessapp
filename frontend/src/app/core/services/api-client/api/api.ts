export * from './ejercicioController.service';
import {EjercicioControllerService} from './ejercicioController.service';
import {EntrenoControllerService} from './entrenoController.service';
import {ItemRutinaControllerService} from './itemRutinaController.service';
import {RutinaControllerService} from './rutinaController.service';

export * from './entrenoController.service';
export * from './itemRutinaController.service';
export * from './rutinaController.service';
export const APIS = [EjercicioControllerService, EntrenoControllerService, ItemRutinaControllerService, RutinaControllerService];
