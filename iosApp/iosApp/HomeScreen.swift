import SwiftUI
import ComposeApp

extension Movie: Identifiable { }

struct HomeScreen: View {
    
    var vm = HomeViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                Observing(vm.state) { state in
                    if state.isLoading {
                        ProgressView()
                            .progressViewStyle(.circular)
                    }
                    
                    if !state.movies.isEmpty {
                        ScrollView {
                            LazyVGrid(columns: [GridItem(.adaptive(minimum: 100))]) {
                                ForEach(state.movies) { movie in
                                    NavigationLink(destination: DetailScreen(movieId: movie.id)) {
                                        MovieItemView(movie: movie)
                                    }
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                }
            }
            .navigationBarTitle("KMP Movies")
        }
        .onAppear {
            vm.onUiReady()
        }
    }
}

struct MovieItemView: View {
    var movie: Movie
    
    var body: some View {
        VStack {
            GeometryReader { geometry in
                ZStack(alignment: .topTrailing) {
                    AsyncImage(url: URL(string: movie.poster)) { phase in
                        switch phase {
                        case .empty:
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle())
                                .frame(width: geometry.size.width, height: geometry.size.height)
                        case .success(let image):
                            image
                                .resizable()
                                .aspectRatio(2 / 3, contentMode: .fill)
                                .frame(width: geometry.size.width, height: geometry.size.height)
                                .clipped()
                                .cornerRadius(8)
                        case .failure:
                            Image(systemName: "photo")
                                .resizable()
                                .aspectRatio(2 / 3, contentMode: .fill)
                                .frame(width: geometry.size.width, height: geometry.size.height)
                                .clipped()
                                .cornerRadius(8)
                        @unknown default:
                            EmptyView()
                        }
                    }
                    
                    
                    if movie.isFavorite {
                        Image(systemName: "heart.fill")
                            .foregroundColor(.white)
                            .padding(5)
                    }
                }
            }
            .aspectRatio(2 / 3, contentMode: .fit)
            
            Text(movie.title)
                .font(.caption)
                .lineLimit(1)
                .padding(5)
        }
    }
}
