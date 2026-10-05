import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ResultHistory } from './result-history';

describe('ResultHistory', () => {
  let component: ResultHistory;
  let fixture: ComponentFixture<ResultHistory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultHistory],
    }).compileComponents();

    fixture = TestBed.createComponent(ResultHistory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
