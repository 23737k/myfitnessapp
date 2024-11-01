export * from './ejercicioController.service';
import { EjercicioControllerService } from './ejercicioController.service';
export * from './entrenoController.service';
import { EntrenoControllerService } from './entrenoController.service';
export * from './itemRutinaController.service';
import { ItemRutinaControllerService } from './itemRutinaController.service';
export * from './rutinaController.service';
import { RutinaControllerService } from './rutinaController.service';
export const APIS = [EjercicioControllerService, EntrenoControllerService, ItemRutinaControllerService, RutinaControllerService];
