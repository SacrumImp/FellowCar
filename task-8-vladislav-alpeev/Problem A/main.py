from Operations.input_package.input import get_input_params


def main():
  size = get_input_params()
  board = Board(size)
  solutions = find_all_solutions(board)
  print_result(solutions)

if __name__ == "__main__":
  main()