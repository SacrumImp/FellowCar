from Data.cell_package.cell import Cell


class Board:

  def __init__(self, side_length):
    self.side_length = side_length
    self.board = [[ Cell.EMPTY for i in range(side_length)] for j in range(side_length)]
  
  def set_cell_value(x, y, value):
    self.board[x][y] = value