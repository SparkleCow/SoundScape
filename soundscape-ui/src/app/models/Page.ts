export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;  // El número de la página actual
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}
